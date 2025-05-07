package com.tealicious.effect;

import com.tealicious.Tealicious;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.w3c.dom.Attr;

public class TealiciousEffects {
    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(BuiltInRegistries.MOB_EFFECT, Tealicious.MODID);

    public static final DeferredHolder<MobEffect, MobEffect> EXPLOSION = EFFECTS.register("explosion", TNTeaEffect::new);

    public static final DeferredHolder<MobEffect, MobEffect> SLEEPY = EFFECTS.register("sleepy", () -> new SleepyEffect()
        .addAttributeModifier(
            Attributes.MOVEMENT_SPEED,
            ResourceLocation.fromNamespaceAndPath(Tealicious.MODID, "effect.sleepy.slowness"),
        -0.15,
            AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL));
}
