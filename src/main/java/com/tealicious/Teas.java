package com.tealicious;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.alchemy.Potion;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class Teas {
    public static final DeferredRegister<Potion> REGISTRY = DeferredRegister.create(BuiltInRegistries.POTION, Tealicious.MODID);

    public static final Holder<Potion> T_N_TEA = REGISTRY.register(
            "t_n_tea",
            () -> new Potion(
                    "t_n_tea",
                    new MobEffectInstance(MobEffects.EXPLOSION)));
}
