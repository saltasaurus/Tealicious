package com.tealicious.client;

import com.tealicious.FluidTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogParameters;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import org.joml.Vector4f;

public class ClientExtensions {
    public static final IClientFluidTypeExtensions HOT_WATER_FLUID = new IClientFluidTypeExtensions() {
        @Override
        public ResourceLocation getStillTexture() {
            return ResourceLocation.withDefaultNamespace("block/water_still");
        }

        @Override
        public ResourceLocation getFlowingTexture() {
            return ResourceLocation.withDefaultNamespace("block/water_flow");
        }

        @Override
        public ResourceLocation getOverlayTexture() {
            return ResourceLocation.withDefaultNamespace("block/water_overlay");
        }

        @Override
        public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
            return ResourceLocation.withDefaultNamespace("textures/misc/underwater.png");
        }

        @Override
        public int getTintColor() {
            return 0xFF3F76E4;
        }

        @Override
        public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
            return 4445678 | 0xFF000000;
        }

        @Override
        public Vector4f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
            return new Vector4f(4 / 255.0F, 31 / 255.0F, 51 / 255.0F, 1F);
        }

        @Override
        public FogParameters modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, FogParameters fogParameters) {
            return new FogParameters(0.0F, 5, fogParameters.shape(), fogParameters.red(), fogParameters.green(), fogParameters.blue(), fogParameters.alpha());
        }
    };

    public static void registerClientItemExtensions(RegisterClientExtensionsEvent event) {
        event.registerFluidType(HOT_WATER_FLUID, FluidTypes.HOT_WATER_FLUID_TYPE.get());
    }
}
