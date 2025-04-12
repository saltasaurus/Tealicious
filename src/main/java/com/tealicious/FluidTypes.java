package com.tealicious;

import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FluidTypes {
    public static final DeferredRegister<FluidType> REGISTRY = DeferredRegister.create(NeoForgeRegistries.FLUID_TYPES, Tealicious.MODID);

    public static final DeferredHolder<FluidType, FluidType> HOT_WATER_FLUID_TYPE = REGISTRY.register(
            "hot_water_type",
            () -> new FluidType(FluidType.Properties.create()
                    .canConvertToSource(true)
                    .supportsBoating(true)));
}
