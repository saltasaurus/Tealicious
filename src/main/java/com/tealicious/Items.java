package com.tealicious;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class Items {
    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(Tealicious.MODID);

    // Creates a new food item with the id "examplemod:example_id", nutrition 1 and saturation 2
//    public static final DeferredItem<Item> EXAMPLE_ITEM = REGISTRY.registerSimpleItem("example_item", new Item.Properties().food(new FoodProperties.Builder()
//            .alwaysEdible().nutrition(1).saturationModifier(2f).build()));

    public static final DeferredItem<BucketItem> HOT_WATER_BUCKET = REGISTRY.register(
            "hot_water_bucket",
            key -> new BucketItem(
                    Fluids.HOT_WATER.get(),
                    new Item.Properties()
                            .setId(ResourceKey.create(Registries.ITEM, key))
                            .craftRemainder(net.minecraft.world.item.Items.BUCKET)
                            .stacksTo(1)));
}
