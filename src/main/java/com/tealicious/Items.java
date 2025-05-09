package com.tealicious;

import com.tealicious.item.TeaItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.Consumables;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Items {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(Tealicious.MODID);

    public static final DeferredItem<BucketItem> HOT_WATER_BUCKET = REGISTRY.register(
            "hot_water_bucket",
            key -> new BucketItem(
                    Fluids.HOT_WATER.get(),
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
                            .craftRemainder(net.minecraft.world.item.Items.BUCKET)
                            .stacksTo(1)));


    public static final DeferredItem<Item> BASIC_TEA = REGISTRY.register(
            "basic_tea",
            (key) -> new TeaItem(
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
                            .food(new FoodProperties.Builder().alwaysEdible().build(), Consumables.DEFAULT_DRINK)
                            .stacksTo(1)));

}
