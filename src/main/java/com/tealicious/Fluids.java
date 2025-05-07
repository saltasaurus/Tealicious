package com.tealicious;

import com.tealicious.item.TealiciousItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.BaseFlowingFluid;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Fluids {
    public static final DeferredRegister<Fluid> REGISTRY = DeferredRegister.create(BuiltInRegistries.FLUID, Tealicious.MODID);

    public static final BaseFlowingFluid.Properties HOT_WATER_PROPERTIES = new BaseFlowingFluid.Properties(
            FluidTypes.HOT_WATER_FLUID_TYPE,
            () -> Fluids.HOT_WATER.get(),
            () -> Fluids.HOT_WATER_FLOWING.get())
            .block(() -> Blocks.HOT_WATER_BLOCK.get())
            .bucket(() -> TealiciousItems.HOT_WATER_BUCKET.get());
    public static final DeferredHolder<Fluid, BaseFlowingFluid> HOT_WATER = REGISTRY.register("hot_water", () -> new BaseFlowingFluid.Source(Fluids.HOT_WATER_PROPERTIES));
    public static final DeferredHolder<Fluid, BaseFlowingFluid> HOT_WATER_FLOWING = REGISTRY.register("hot_water_flowing", () -> new BaseFlowingFluid.Flowing(Fluids.HOT_WATER_PROPERTIES));
}
