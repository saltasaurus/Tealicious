package com.tealicious.effect;

import com.tealicious.Tealicious;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MobEffects {
    public static final DeferredRegister<MobEffect> REGISTRY = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Tealicious.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> EXPLOSION = REGISTRY.register("explosion", TNTeaEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> SLEEPY = REGISTRY.register("sleepy", () -> new SleepyEffect()
        .addAttributeModifier(
                Attributes.MOVEMENT_SPEED,
                ResourceLocation.fromNamespaceAndPath(Tealicious.MODID, "effect.sleepy.slowness"),
                -0.2,
                AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL
        ).addAttributeModifier(
                Attributes.ATTACK_SPEED,
                ResourceLocation.fromNamespaceAndPath(Tealicious.MODID, "effect.sleepy.attack_fatigue"),
                4,
                AttributeModifier.Operation.ADD_VALUE
        ));
}
