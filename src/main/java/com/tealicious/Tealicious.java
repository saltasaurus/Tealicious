package com.tealicious;

import com.tealicious.client.ClientExtensions;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Tealicious.MODID)
public class Tealicious {
    public static final String MODID = "tealicious";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Tealicious(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        Blocks.REGISTRY.register(modEventBus);
        Items.REGISTRY.register(modEventBus);
        Fluids.REGISTRY.register(modEventBus);
        FluidTypes.REGISTRY.register(modEventBus);
        CreativeModeTabs.REGISTRY.register(modEventBus);

        if (dist == Dist.CLIENT) {
            modEventBus.addListener(ClientExtensions::registerClientItemExtensions);
        }
    }
}
