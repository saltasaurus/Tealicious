package com.tealicious;

import com.tealicious.item.TealiciousItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class CreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Tealicious.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> TEALICIOUS_TAB = REGISTRY.register("tealicious", () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.tealicious")) //The language key for the title of your CreativeModeTab
            .withTabsBefore(net.minecraft.world.item.CreativeModeTabs.COMBAT)
            .icon(() -> TealiciousItems.HOT_WATER_BUCKET.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(TealiciousItems.HOT_WATER_BUCKET.get());
            }).build());
}
