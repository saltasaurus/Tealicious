package com.tealicious;

import com.tealicious.effect.TNTeaEffect;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Tealicious.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> EXPLOSION = REGISTRY.register("explosion", () -> new TNTeaEffect());
}
