package dev.JustRed23.idk.particle.paint;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.JustRed23.idk.ModParticles;
import net.minecraft.commands.arguments.blocks.BlockStateParser;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class PaintParticleData implements ParticleOptions {

    public static final Deserializer<PaintParticleData> DESERIALIZER = new Deserializer<>() {
        public PaintParticleData fromCommand(ParticleType<PaintParticleData> type, StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            BlockState blockstate = BlockStateParser.parseForBlock(BuiltInRegistries.BLOCK.asLookup(), reader, false).blockState();
            reader.expect(' ');
            int color = reader.readInt();
            return new PaintParticleData(blockstate, color);
        }

        public PaintParticleData fromNetwork(ParticleType<PaintParticleData> type, FriendlyByteBuf buf) {
            return new PaintParticleData(buf.readById(Block.BLOCK_STATE_REGISTRY), buf.readInt());
        }
    };
    public static final Codec<PaintParticleData> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BlockState.CODEC.fieldOf("blockstate").forGetter(PaintParticleData::getBlockstate),
            Codec.INT.fieldOf("color").forGetter(PaintParticleData::getColor)
    ).apply(instance, PaintParticleData::new));

    private final BlockState blockstate;
    private final int color;

    public PaintParticleData(BlockState state, int color) {
        this.blockstate = state;
        this.color = color;
    }

    public @NotNull ParticleType<PaintParticleData> getType() {
        return ModParticles.PAINT_PARTICLE.get();
    }

    public BlockState getBlockstate() {
        return blockstate;
    }

    public int getColor() {
        return color;
    }

    public void writeToNetwork(FriendlyByteBuf buf) {
        buf.writeId(Block.BLOCK_STATE_REGISTRY, this.blockstate);
        buf.writeInt(this.color);
    }

    public @NotNull String writeToString() {
        return String.format("%s %s %d", ForgeRegistries.PARTICLE_TYPES.getKey(this.getType()), BlockStateParser.serialize(this.blockstate), this.color);
    }

    //FORGE: Add a source pos property, so we can provide models with additional model data
    private static net.minecraft.core.BlockPos pos;
    public PaintParticleData setPos(net.minecraft.core.BlockPos pos) {
        PaintParticleData.pos = pos;
        return this;
    }

    public net.minecraft.core.BlockPos getPos() {
        return pos;
    }
}
