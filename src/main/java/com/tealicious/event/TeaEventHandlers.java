package com.tealicious.event;

import com.tealicious.capability.TeaCapabilities;
import com.tealicious.Tealicious;
import com.tealicious.item.ChamomileTeaItem;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import net.neoforged.neoforge.event.entity.player.PlayerWakeUpEvent;

@Mod.EventBusSubscriber(modid = Tealicious.MOD_ID)
public class TeaEventHandlers {
    // Player tick com.tealicious.event to handle our custom tea effects
    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide()) {
            Player player = event.player;

            player.getCapability(TeaCapabilities.TEA_EFFECTS).ifPresent(teaEffects -> {
                // Process tea effects
                teaEffects.tickEffects(player);
            });
        }
    }

    // Handle player sleeping to grant sleep buff
    @SubscribeEvent
    public static void onPlayerWakeUp(PlayerWakeUpEvent event) {
        Player player = event.getEntity();

        // Only process if player actually slept (not just setting spawn point)
        // Also check if player has sleepiness com.tealicious.effect
        if (!player.level().isClientSide() && event.updateLevel() &&
                player.hasEffect(ModEffects.SLEEPINESS.get())) {

            player.getCapability(TeaCapabilities.TEA_EFFECTS).ifPresent(teaEffects -> {
                // Get stack level for chamomile tea
                int stackLevel = teaEffects.getTeaStackLevel(ChamomileTeaItem.TEA_TYPE);

                if (stackLevel > 0) {
                    // Player slept with chamomile tea com.tealicious.effect - grant buff!
                    MobEffectInstance effect = player.getEffect(ModEffects.SLEEPINESS.get());
                    int amplifier = effect != null ? effect.getAmplifier() : 0;

                    // Calculate buff duration based on remaining sleepiness and stack level
                    int remainingDuration = effect != null ? effect.getDuration() : 0;
                    int buffDuration = remainingDuration / 2 + (stackLevel * 20 * 30); // Half remaining + 30s per stack
                    buffDuration = Math.max(buffDuration, 60 * 20); // At least 60 seconds

                    // Apply buff
                    teaEffects.setSleepBuff(true, buffDuration);

                    // Remove sleepiness com.tealicious.effect
                    player.removeEffect(ModEffects.SLEEPINESS.get());

                    // Clear tea stack
                    teaEffects.clearTeaStack(ChamomileTeaItem.TEA_TYPE);

                    // Visual and sound effects
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.PLAYERS, 0.5F, 1.0F);

                    ((ServerLevel)player.level()).sendParticles(
                            ParticleTypes.HAPPY_VILLAGER,
                            player.getX(), player.getY() + 1, player.getZ(),
                            15, 0.5, 0.5, 0.5, 0.1
                    );

                    // Notify player
                    player.displayClientMessage(
                            Component.translatable("com.tealicious.effect.tealicious.chamomile_buff"),
                            true
                    );
                }
            });
        }
    }

    // Handle com.tealicious.effect expiry to apply debuffs if player doesn't sleep
    @SubscribeEvent
    public static void onEffectRemoved(MobEffectEvent.Remove event) {
        // Check if it's our sleepiness com.tealicious.effect being removed
        if (event.getEffect().getEffect() == ModEffects.SLEEPINESS.get() &&
                event.getEntity() instanceof ServerPlayer player) {

            player.getCapability(TeaCapabilities.TEA_EFFECTS).ifPresent(teaEffects -> {
                // Only apply debuffs if player has chamomile stacks and hasn't slept
                int stackLevel = teaEffects.getTeaStackLevel(ChamomileTeaItem.TEA_TYPE);

                if (stackLevel > 0 && !teaEffects.hasSleepBuff()) {
                    // Apply debuffs based on stack level
                    int amplifier = stackLevel - 1; // 0, 1, 2

                    // Drowsiness com.tealicious.effect
                    player.addEffect(new MobEffectInstance(
                            MobEffects.MOVEMENT_SLOWDOWN,
                            (30 + amplifier * 15) * 20, // 30/45/60 seconds
                            amplifier,
                            false, true, false
                    ));

                    // Weakness
                    player.addEffect(new MobEffectInstance(
                            MobEffects.WEAKNESS,
                            (20 + amplifier * 10) * 20, // 20/30/40 seconds
                            amplifier,
                            false, true, false
                    ));

                    // Add blindness at higher stacks
                    if (amplifier >= 1) {
                        player.addEffect(new MobEffectInstance(
                                MobEffects.BLINDNESS,
                                10 * 20, // 10 seconds
                                0,
                                false, true, false
                        ));
                    }

                    // Visual and sound effects for drowsiness
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ZOMBIE_AMBIENT, SoundSource.PLAYERS, 0.2F, 0.5F);

                    ((ServerLevel)player.level()).sendParticles(
                            ParticleTypes.FALLING_WATER,
                            player.getX(), player.getY() + 1.8, player.getZ(),
                            20, 0.3, 0.3, 0.3, 0.01
                    );

                    // Notify player
                    player.displayClientMessage(
                            Component.translatable("com.tealicious.effect.tealicious.chamomile_drowsy"),
                            true
                    );

                    // Clear tea stack
                    teaEffects.clearTeaStack(ChamomileTeaItem.TEA_TYPE);
                }
            });
        }
    }
}