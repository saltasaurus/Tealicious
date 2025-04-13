package com.tealicious.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class TNTeaEffect extends InstantenousMobEffect {
    public TNTeaEffect() {
        super(MobEffectCategory.HARMFUL, 13118248);
    }

    @Override
    public void applyInstantenousEffect(ServerLevel serverLevel, @Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
        serverLevel.explode(
                null,
                serverLevel.damageSources().explosion(null),
                null,
                livingEntity.getX(),
                livingEntity.getY(),
                livingEntity.getZ(),
                2f,
                false,
                Level.ExplosionInteraction.BLOCK);
    }
}
