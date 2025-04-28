package com.tealicious.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public class SleepyEffect extends MobEffect {
    public SleepyEffect() {
        super(MobEffectCategory.NEUTRAL, 0xFFFF00); }
}
