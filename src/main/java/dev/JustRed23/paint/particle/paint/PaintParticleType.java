package dev.JustRed23.paint.particle.paint;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import org.jetbrains.annotations.NotNull;

public class PaintParticleType extends ParticleType<PaintParticleData> {

    public PaintParticleType() {
        super(false, PaintParticleData.DESERIALIZER);
    }

    public @NotNull Codec<PaintParticleData> codec() {
        return PaintParticleData.CODEC;
    }
}
