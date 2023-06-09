package dev.JustRed23.idk;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModSoundEvents {

    private static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, IDK.MOD_ID);

    static void register(IEventBus bus) {
        SOUND_EVENTS.register(bus);
    }

    private static RegistryObject<SoundEvent> register(String name) {
        return register(name, SoundEvent.createVariableRangeEvent(new ResourceLocation(IDK.MOD_ID, name)));
    }

    private static RegistryObject<SoundEvent> register(String name, float range) {
        return register(name, SoundEvent.createFixedRangeEvent(new ResourceLocation(IDK.MOD_ID, name), range));
    }

    private static RegistryObject<SoundEvent> register(String name, SoundEvent soundEvent) {
        return SOUND_EVENTS.register(name, () -> soundEvent);
    }

    //SOUND EVENTS
    public static final RegistryObject<SoundEvent> PAINT = register("paint");
}
