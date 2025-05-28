package com.tealicious.item;

import com.tealicious.MobEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HerbalTeaItem extends TeaItem {

    public HerbalTeaItem(Properties properties) {
        super(properties);
    }

    @Override
    protected void applyTeaEffects(ItemStack stack, Level level, Player player) {

        if (!level.isClientSide() && player != null) {
            // Apply bloom effect for 1 minute (1200 ticks = 60 seconds)
            MobEffectInstance bloomEffect = new MobEffectInstance(MobEffects.BLOOM, 1200, 0, false, true, true);
            player.addEffect(bloomEffect);
        }
    }
}