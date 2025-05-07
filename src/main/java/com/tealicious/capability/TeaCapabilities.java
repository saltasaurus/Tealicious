package com.tealicious.capability;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;

// Register the com.tealicious.capability
public class TeaCapabilities {
    public static final Capability<ITeaEffects> TEA_EFFECTS = CapabilityManager.get(
            new CapabilityToken<>(){});

    public static void register() {
        // Register com.tealicious.capability
        RegisterCapabilitiesEvent.register(ITeaEffects.class);

        // Attach com.tealicious.capability to players when they're created
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, TeaCapabilities::attachCapability);
    }

    private static void attachCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            TeaEffectsProvider provider = new TeaEffectsProvider();
            event.addCapability(new ResourceLocation(Tealicious.MOD_ID, "tea_effects"), provider);
            event.addListener(provider::invalidate);
        }
    }

    // Provider implementation
    public static class TeaEffectsProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
        private final LazyOptional<ITeaEffects> instance = LazyOptional.of(TeaEffectsCapability::new);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
            return TEA_EFFECTS.orEmpty(cap, instance.cast());
        }

        @Override
        public CompoundTag serializeNBT() {
            return instance.map(effects -> ((TeaEffectsCapability)effects).serializeNBT())
                    .orElse(new CompoundTag());
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            instance.ifPresent(effects -> ((TeaEffectsCapability)effects).deserializeNBT(nbt));
        }

        void invalidate() {
            instance.invalidate();
        }
    }
}