package com.tealicious;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Teas {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(BuiltInRegistries.POTION, Tealicious.MODID);

    public static final Holder<Potion> T_N_TEA = REGISTRY.register(
            "t_n_tea",
            () -> new Potion(
                    "t_n_tea",
                    new MobEffectInstance(MobEffects.EXPLOSION, 80)));

    public static final Holder<Potion> CHAMOMILE = REGISTRY.register(
            "chamomile",
            () -> new Potion(
                    "chamomile",
                    // Duration in ticks (20 ticks/s)
                    new MobEffectInstance(MobEffects.SLEEPY, 1200)));
}
