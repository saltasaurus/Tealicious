package com.tealicious;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Blocks {
    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(Tealicious.MODID);

    public static final DeferredBlock<LiquidBlock> HOT_WATER_BLOCK = REGISTRY.registerBlock(
            "hot_water_block",
            properties -> new LiquidBlock(
                Fluids.HOT_WATER_FLOWING.get(), properties),
                BlockBehaviour.Properties.ofFullCopy(net.minecraft.world.level.block.Blocks.WATER));
}
