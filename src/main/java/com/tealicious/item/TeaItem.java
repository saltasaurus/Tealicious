package com.tealicious.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemUtils;

/**
 * Base class for all tea items in the mod.
 * Provides common functionality for tea consumption and effects.
 */
public class TeaItem extends Item {
    public TeaItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.DRINK;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity entity) {
        return 32;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        // Check if the player can consume the tea
        if (player.canEat(false)) {
            return ItemUtils.startUsingInstantly(level, player, hand);
        }

        return InteractionResult.PASS;
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            applyTeaEffects(stack, level, player);
        }

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            stack.shrink(1);

//            player.die(player.damageSources().cactus());
//            player.hurt(player.damageSources().cactus(), 1000);
//            player.setNoGravity(true);

            // Return empty cup/container if needed
            // If you want to return an empty cup, implement that here
        }

        return stack;
    }

    /**
     * Apply the effects of this tea when consumed.
     * Override this method in subclasses to implement specific tea effects.
     */
    protected void applyTeaEffects(ItemStack stack, Level level, Player player) {
        // Base implementation does nothing
        // Subclasses will override this to add custom effects
    }
}