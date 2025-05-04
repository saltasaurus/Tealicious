package com.tealicious;

import com.tealicious.client.ClientExtensions;
import com.tealicious.effect.MobEffects;
import com.tealicious.effect.TNTeaEffect;
import net.minecraft.world.item.alchemy.PotionBrewing;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.brewing.RegisterBrewingRecipesEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;

@Mod(Tealicious.MODID)
public class Tealicious {
    public static final String MODID = "tealicious";

    public static final Logger LOGGER = LogUtils.getLogger();

    public Tealicious(IEventBus modEventBus, ModContainer modContainer, Dist dist) {
        Blocks.REGISTRY.register(modEventBus);
        Items.REGISTRY.register(modEventBus);
        Fluids.REGISTRY.register(modEventBus);
        FluidTypes.REGISTRY.register(modEventBus);
        CreativeModeTabs.REGISTRY.register(modEventBus);
        MobEffects.REGISTRY.register(modEventBus);
        Teas.REGISTRY.register(modEventBus);


        NeoForge.EVENT_BUS.register(this);

        NeoForge.EVENT_BUS.addListener(TNTeaEffect::onEffectExpired);

        if (dist == Dist.CLIENT) {
            modEventBus.addListener(ClientExtensions::registerClientItemExtensions);
        }
    }

    // Using some method to listen to the event
    @SubscribeEvent
    public void registerBrewingRecipes(RegisterBrewingRecipesEvent event) {
        // Gets the builder to add recipes to
        PotionBrewing.Builder builder = event.getBuilder();

        builder.addMix(
                Potions.WATER,
                net.minecraft.world.item.Items.TNT,
                Teas.T_N_TEA
        );

        builder.addMix(
                Potions.WATER,
                net.minecraft.world.item.Items.OXEYE_DAISY,
                Teas.CHAMOMILE
        );
    }
}
