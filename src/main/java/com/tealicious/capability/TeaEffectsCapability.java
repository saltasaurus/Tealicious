package com.tealicious.capability;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

// Implement the com.tealicious.capability
public class TeaEffectsCapability implements ITeaEffects {
    private final Map<String, Integer> teaStacks = new HashMap<>();
    private boolean hasSleepBuff = false;
    private int sleepBuffTimeRemaining = 0;

    @Override
    public int getTeaStackLevel(String teaType) {
        return teaStacks.getOrDefault(teaType, 0);
    }

    @Override
    public void addTeaStack(String teaType, int maxStacks) {
        int current = getTeaStackLevel(teaType);
        teaStacks.put(teaType, Math.min(current + 1, maxStacks));
    }

    @Override
    public void clearTeaStack(String teaType) {
        teaStacks.remove(teaType);
    }

    @Override
    public boolean hasSleepBuff() {
        return hasSleepBuff;
    }

    @Override
    public void setSleepBuff(boolean value, int duration) {
        this.hasSleepBuff = value;
        this.sleepBuffTimeRemaining = value ? duration : 0;
    }

    @Override
    public void updateSleepBuff() {
        if (sleepBuffTimeRemaining > 0) {
            sleepBuffTimeRemaining--;
            if (sleepBuffTimeRemaining <= 0) {
                hasSleepBuff = false;
            }
        }
    }

    @Override
    public int getSleepBuffTimeRemaining() {
        return sleepBuffTimeRemaining;
    }

    @Override
    public Map<String, Integer> getAllTeaStacks() {
        return Collections.unmodifiableMap(teaStacks);
    }

    @Override
    public void tickEffects(Player player) {
        // Update sleep buff timer
        updateSleepBuff();

        // Apply sleep buff effects if active
        if (hasSleepBuff && !player.level().isClientSide()) {
            // Apply damage reduction via attribute modifier
            AttributeInstance attribute = player.getAttribute(Attributes.GENERIC_ARMOR_TOUGHNESS);
            UUID sleepBuffModifierId = UUID.fromString("1e3b3838-bc4c-4cfb-b254-d3f2b9911f38");

            // Remove any existing modifier
            if (attribute != null && attribute.getModifier(sleepBuffModifierId) == null) {
                // Add 10% damage reduction
                attribute.addTransientModifier(new AttributeModifier(
                        sleepBuffModifierId,
                        "Chamomile sleep buff",
                        2.0, // 2 points of toughness
                        AttributeModifier.Operation.ADD_VALUE
                ));
            }

            // Add health boost if not already applied
            if (!player.hasEffect(MobEffects.HEALTH_BOOST)) {
                player.addEffect(new MobEffectInstance(
                        MobEffects.HEALTH_BOOST,
                        sleepBuffTimeRemaining,
                        0, // 2 hearts (4 health points)
                        false,
                        false
                ));
            }
        }

        // Handle sleepiness effects if the player has the com.tealicious.effect
        MobEffectInstance sleepinessEffect = player.getEffect(ModEffects.SLEEPINESS.get());
        if (sleepinessEffect != null) {
            int duration = sleepinessEffect.getDuration();
            int amplifier = sleepinessEffect.getAmplifier();
            int maxDuration = (30 + amplifier * 15) * 20; // 30 seconds + 15 per level
            float progress = 1.0f - (float)duration / maxDuration;

            // As the com.tealicious.effect nears completion, apply stronger effects
            if (progress > 0.7f) {
                // Apply increasing drowsiness effects
                int drowsinessLevel = (int)Math.floor(progress * 3); // 0, 1, 2 based on progress

                if (drowsinessLevel > 0) {
                    player.addEffect(new MobEffectInstance(
                            MobEffects.MOVEMENT_SLOWDOWN,
                            40, // 2 seconds
                            drowsinessLevel - 1,
                            false,
                            false
                    ));

                    // Add blindness in the final stage
                    if (drowsinessLevel >= 2) {
                        player.addEffect(new MobEffectInstance(
                                MobEffects.BLINDNESS,
                                40, // 2 seconds
                                0,
                                false,
                                false
                        ));
                    }
                }

                // Visual particle effects
                if (!player.level().isClientSide() && player.getRandom().nextFloat() < 0.2f) {
                    ((ServerLevel)player.level()).sendParticles(
                            ParticleTypes.FALLING_WATER,
                            player.getX(), player.getY() + 1.8, player.getZ(),
                            5, 0.3, 0.2, 0.3, 0.01
                    );
                }
            }
        }
    }

    // Handle com.tealicious.capability serialization/deserialization
    public CompoundTag serializeNBT() {
        CompoundTag tag = new CompoundTag();
        CompoundTag stacks = new CompoundTag();

        for (Map.Entry<String, Integer> entry : teaStacks.entrySet()) {
            stacks.putInt(entry.getKey(), entry.getValue());
        }

        tag.put("teaStacks", stacks);
        tag.putBoolean("hasSleepBuff", hasSleepBuff);
        tag.putInt("sleepBuffTime", sleepBuffTimeRemaining);

        return tag;
    }

    public void deserializeNBT(CompoundTag tag) {
        teaStacks.clear();

        CompoundTag stacks = tag.getCompound("teaStacks");
        for (String key : stacks.getAllKeys()) {
            teaStacks.put(key, stacks.getInt(key));
        }

        hasSleepBuff = tag.getBoolean("hasSleepBuff");
        sleepBuffTimeRemaining = tag.getInt("sleepBuffTime");
    }
}
