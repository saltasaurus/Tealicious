package com.tealicious;

import com.tealicious.effect.TealiciousEffects;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Teas {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(BuiltInRegistries.POTION, Tealicious.MODID);

    public static final Holder<Potion> T_N_TEA = REGISTRY.register(
            "t_n_tea",
            () -> new Potion(
                    "t_n_tea",
                    new MobEffectInstance(TealiciousEffects.EXPLOSION, 80)));

    public static final Holder<Potion> CHAMOMILE = REGISTRY.register(
            "chamomile", () -> new Potion("chamomile",
                    // Duration in ticks (20 ticks/s)
                    new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 2),
                    new MobEffectInstance(MobEffects.WEAKNESS, 200, 1),
                    new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 200, 1),
                    new MobEffectInstance(MobEffects.REGENERATION, 200, 3)
                    ));


}
