package dev.JustRed23.paint.blocks.paintedblockvariants;

import dev.JustRed23.paint.ModBlockEntities;
import dev.JustRed23.paint.blocks.PaintedBlock;
import dev.JustRed23.paint.blocks.blockentities.PaintedBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaintedGlass extends PaintedBlock {

    public PaintedGlass() {
        super(BlockBehaviour.Properties.copy(Blocks.GLASS).noParticlesOnBreak());
    }

    public BlockEntityType<PaintedBlockEntity> getBlockEntityType() {
        return ModBlockEntities.PAINTED_GLASS_ENTITY.get();
    }

    public float @Nullable [] getBeaconColorMultiplier(BlockState state, LevelReader level, BlockPos pos, BlockPos beaconPos) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (blockEntity instanceof PaintedBlockEntity paintedBlockEntity) {
            int color = paintedBlockEntity.getColor();
            float[] rgb = new float[3];
            rgb[0] = (float) (color >> 16 & 255) / 255.0F;
            rgb[1] = (float) (color >> 8 & 255) / 255.0F;
            rgb[2] = (float) (color & 255) / 255.0F;
            return rgb;
        }
        return null;
    }

    public @NotNull VoxelShape getVisualShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext p_48738_) {
        return Shapes.empty();
    }

    public float getShadeBrightness(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        return 1.0F;
    }

    public boolean propagatesSkylightDown(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos) {
        return true;
    }

    public boolean skipRendering(@NotNull BlockState state, BlockState neighbour, @NotNull Direction direction) {
        if (neighbour.getBlock() instanceof PaintedGlass || neighbour.getBlock() instanceof AbstractGlassBlock)
            return true;

        return super.skipRendering(state, neighbour, direction);
    }
}
