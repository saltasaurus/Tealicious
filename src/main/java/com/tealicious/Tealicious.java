package com.tealicious;

import com.tealicious.item.TealiciousItems;
import com.tealicious.client.ClientExtensions;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

//public class ModEffects {
//    public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(
//            ForgeRegistries.MOB_EFFECTS, Tealicious.MOD_ID);
//
//    public static final RegistryObject<MobEffect> SLEEPINESS = EFFECTS.register(
//            "sleepiness", SleepinessEffect::new);
//
//    // Other effects...
//}
//
//public class ModItems {
//    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(
//            ForgeRegistries.ITEMS, Tealicious.MOD_ID);
//
//    public static final RegistryObject<Item> CHAMOMILE_TEA = ITEMS.register(
//            "chamomile_tea", ChamomileTeaItem::new);
//
//    // Other tea items...
//}
//
//// In your main mod class
//@Mod(Tealicious.MOD_ID)
//public class Tealicious {
//    public static final String MOD_ID = "tealicious";
//
//    public Tealicious() {
//        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//
//        // Register DeferredRegisters
//        ModEffects.EFFECTS.register(modEventBus);
//        ModItems.ITEMS.register(modEventBus);
//
//        // Register capabilities
//        TeaCapabilities.register();
//
//        // Register mod com.tealicious.event handlers
//        MinecraftForge.EVENT_BUS.register(TeaEventHandlers.class);
//    }
//}

@Mod(Tealicious.MODID)
public class Tealicious {
    public static final String MODID = "tealicious";

    private static final Logger LOGGER = LogUtils.getLogger();

    public Tealicious(IEventBus modEventBus, ModContainer modContainer, Dist dist) {

        DeferredRegister<?>[] registers = {
            Blocks.REGISTRY,
            TealiciousItems.REGISTRY,
            Fluids.REGISTRY,
            FluidTypes.REGISTRY,
            CreativeModeTabs.REGISTRY,
        };

        for (DeferredRegister<?> register : registers) {
            register.register(modEventBus);
        }

        NeoForge.EVENT_BUS.register(this);

        if (dist == Dist.CLIENT) {
            modEventBus.addListener(ClientExtensions::registerClientItemExtensions);
        }
    }
}

