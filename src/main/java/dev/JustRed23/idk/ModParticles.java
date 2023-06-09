package dev.JustRed23.idk;

import dev.JustRed23.idk.particle.paint.PaintParticleData;
import dev.JustRed23.idk.particle.paint.PaintParticleType;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModParticles {

    private static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, IDK.MOD_ID);

    static void register(IEventBus bus) {
        PARTICLE_TYPES.register(bus);
    }

    //PARTICLE TYPES
    public static final RegistryObject<ParticleType<PaintParticleData>> PAINT_PARTICLE = PARTICLE_TYPES.register("paint_particle", PaintParticleType::new);
}
