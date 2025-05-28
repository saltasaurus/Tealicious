package com.tealicious.effect;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.jline.utils.Log;

public class BloomEffect extends MobEffect {

    public BloomEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x90EE90); // Light green color
    }

    @Override
    public boolean applyEffectTick(ServerLevel serverLevel, LivingEntity livingEntity, int amplifier) {
        BlockPos playerPos = livingEntity.blockPosition();

        // Apply bonemeal effect to multiple random locations 5x2x5 around player
        for (int i = 0; i < 4; i++) {
            int randomX = serverLevel.getRandom().nextInt(11) - 6;
            int randomZ = serverLevel.getRandom().nextInt(11) - 6;
            int randomY = serverLevel.getRandom().nextInt(2) - 1;

            BlockPos targetPos = playerPos.offset(randomX, randomY, randomZ);
            tryBonemealAt(serverLevel, targetPos);
            tryBonemealAt(serverLevel, targetPos.above());
        }

        return true; // Effect continues
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        // Apply every 5 ticks (0.25 seconds at 20 ticks per second)
        return duration % 5 == 0;
    }

    private void tryBonemealAt(ServerLevel level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);

        // Only apply bonemeal to farming-related blocks
        if (!isFarmingBlock(blockState)) {
            return;
        }

        if (BoneMealItem.applyBonemeal(ItemStack.EMPTY, level, pos, null)) {
            level.levelEvent(1505, pos, 0); // Bonemeal particle effect
        }

    }

    private boolean isFarmingBlock(BlockState blockState) {
        return blockState.is(Blocks.WHEAT) ||
                blockState.is(Blocks.CARROTS) ||
                blockState.is(Blocks.POTATOES) ||
                blockState.is(Blocks.BEETROOTS) ||
                blockState.is(Blocks.PUMPKIN_STEM) ||
                blockState.is(Blocks.MELON_STEM) ||
                blockState.is(Blocks.COCOA) ||
                blockState.is(Blocks.SWEET_BERRY_BUSH) ||
                blockState.is(Blocks.BAMBOO_SAPLING) ||
                blockState.is(Blocks.BAMBOO) ||
                // Tree saplings
                blockState.is(Blocks.OAK_SAPLING) ||
                blockState.is(Blocks.BIRCH_SAPLING) ||
                blockState.is(Blocks.SPRUCE_SAPLING) ||
                blockState.is(Blocks.JUNGLE_SAPLING) ||
                blockState.is(Blocks.ACACIA_SAPLING) ||
                blockState.is(Blocks.DARK_OAK_SAPLING) ||
                blockState.is(Blocks.CHERRY_SAPLING) ||
                blockState.is(Blocks.MANGROVE_PROPAGULE);
    }
}