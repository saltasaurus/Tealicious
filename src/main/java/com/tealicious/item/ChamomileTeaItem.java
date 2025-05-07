package com.tealicious.item;

import com.tealicious.capability.TeaCapabilities;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ChamomileTeaItem extends Item {
    public static final String TEA_TYPE = "chamomile";
    public static final int MAX_STACKS = 3;

    public ChamomileTeaItem() {
        super(new Item.Properties()
                .food(new FoodProperties.Builder()
                        .nutrition(2)
                        .saturationMod(0.6f)
                        .build())
                .stacksTo(16)
                .tab(CreativeModeTab.TAB_FOOD)); // Use your mod's creative tab
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack resultStack = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide() && entity instanceof ServerPlayer player) {
            // Apply sleepiness com.tealicious.effect with stacking behavior
            player.getCapability(TeaCapabilities.TEA_EFFECTS).ifPresent(teaEffects -> {
                // Get current stack level and increase it
                int currentStack = teaEffects.getTeaStackLevel(TEA_TYPE);
                teaEffects.addTeaStack(TEA_TYPE, MAX_STACKS);

                // Get new stack level
                int newStack = teaEffects.getTeaStackLevel(TEA_TYPE);

                // Apply sleepiness com.tealicious.effect with appropriate amplifier
                int amplifier = newStack - 1; // 0-based amplifier (0, 1, 2)
                int duration = (30 + amplifier * 15) * 20; // 30/45/60 seconds

                // Apply or extend the com.tealicious.effect
                MobEffectInstance existingEffect = player.getEffect(ModEffects.SLEEPINESS.get());
                if (existingEffect != null) {
                    // If already active, use the higher amplifier and add some duration
                    int newAmplifier = Math.max(existingEffect.getAmplifier(), amplifier);
                    int newDuration = Math.max(existingEffect.getDuration(), duration);
                    newDuration = Math.min(newDuration + 10 * 20, 120 * 20); // Add 10s, cap at 2 mins

                    player.removeEffect(ModEffects.SLEEPINESS.get());
                    player.addEffect(new MobEffectInstance(
                            ModEffects.SLEEPINESS.get(),
                            newDuration,
                            newAmplifier,
                            false, // Don't hide particles
                            true,  // Show icon
                            false  // Not ambient
                    ));
                } else {
                    // Apply new com.tealicious.effect
                    player.addEffect(new MobEffectInstance(
                            ModEffects.SLEEPINESS.get(),
                            duration,
                            amplifier,
                            false,
                            true,
                            false
                    ));
                }

                // Play relaxing drinking sound
                level.playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.HONEY_DRINK, SoundSource.PLAYERS, 0.5F, 0.8F);

                // Show visual com.tealicious.effect
                ((ServerLevel)level).sendParticles(
                        ParticleTypes.FALLING_HONEY,
                        player.getX(), player.getY() + 1, player.getZ(),
                        10, 0.4, 0.3, 0.4, 0.01
                );

                // Send message to player
                Component message;
                if (newStack > 1) {
                    message = Component.translatable("com.tealicious.item.tealicious.chamomile_tea.stacked", newStack);
                } else {
                    message = Component.translatable("com.tealicious.item.tealicious.chamomile_tea.com.tealicious.effect");
                }
                player.displayClientMessage(message, true);
            });
        }

        return resultStack;
    }

    // Add tooltip info
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);
        tooltip.add(Component.translatable("com.tealicious.item.tealicious.chamomile_tea.tooltip")
                .withStyle(ChatFormatting.BLUE));
        tooltip.add(Component.translatable("com.tealicious.item.tealicious.chamomile_tea.tooltip2")
                .withStyle(ChatFormatting.ITALIC, ChatFormatting.GRAY));
    }
}
