package com.tealicious.effect;

import com.tealicious.MobEffects;
import com.tealicious.Tealicious;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.InstantenousMobEffect;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;

import javax.annotation.Nullable;

public class TNTeaEffect extends MobEffect {
    public TNTeaEffect() {
        super(MobEffectCategory.HARMFUL, 13118248);
    }

    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        super.onEffectAdded(entity, amplifier);

        entity.level().playSound(null, entity.blockPosition(), SoundEvents.TNT_PRIMED, SoundSource.BLOCKS, 0.5F, 1.0F);
    }

    public static void onEffectExpired(MobEffectEvent.Expired event) {
        LivingEntity entity = event.getEntity();
        Holder<MobEffect> effect = event.getEffectInstance().getEffect();
        if (effect.is(MobEffects.EXPLOSION)) {
            entity.level().explode(
                    null,
                    entity.level().damageSources().explosion(null),
                    null,
                    entity.getX(),
                    entity.getY() + 1,
                    entity.getZ(),
                    2f,
                    true,
                    Level.ExplosionInteraction.BLOCK);
        }
    }
}
