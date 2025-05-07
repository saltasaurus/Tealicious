package com.tealicious.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class SleepinessEffect extends MobEffect {
    public SleepinessEffect() {
        // Light blue color for sleepiness
        super(MobEffectCategory.NEUTRAL, 0xB0E0E6);
    }

    @Override
    public boolean applyEffectTick(@NotNull ServerLevel level, @NotNull LivingEntity entity, int amplifier) {
        // Apply basic visual effects - these will be subtle indicators
        // The main logic will be handled in the com.tealicious.capability system
        if (entity instanceof Player player && !player.level().isClientSide()) {
            // Get the remaining duration as a percentage
            int duration = player.getEffect(this).getDuration();
            int maxDuration = 30 * 20; // Assume 30 seconds base duration
            float progress = 1.0f - (float)duration / maxDuration;

            // As com.tealicious.effect progresses, occasionally apply brief visual effects
            if (player.getRandom().nextFloat() < 0.1f * (progress + 0.1f)) {
                // Brief slowness to simulate stumbling/drowsiness
                player.addEffect(new MobEffectInstance(
                        MobEffects.MOVEMENT_SLOWDOWN,
                        20, // 1 second
                        0,
                        false, // Don't show particles
                        false, // Don't show icon (we already have sleepiness icon)
                        true   // Is ambient
                ));
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // Run the com.tealicious.effect every second
        return duration % 20 == 0;
    }
}
