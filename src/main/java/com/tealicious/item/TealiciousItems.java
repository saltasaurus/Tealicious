package com.tealicious.item;

import com.tealicious.Fluids;
import com.tealicious.Tealicious;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class TealiciousItems {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(Tealicious.MODID);

    public static final DeferredItem<BucketItem> HOT_WATER_BUCKET = REGISTRY.register(
            "hot_water_bucket",
            key -> new BucketItem(
                    Fluids.HOT_WATER.get(),
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
                            .craftRemainder(net.minecraft.world.item.Items.BUCKET)
                            .stacksTo(1)));
}
