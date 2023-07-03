package dev.JustRed23.paint.blocks.blockentities;

import dev.JustRed23.paint.blocks.blockentities.template.ColoredBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class PaintedBlockEntity extends ColoredBlockEntity {

    public PaintedBlockEntity(BlockEntityType type, BlockPos p_155229_, BlockState p_155230_) {
        super(type, p_155229_, p_155230_);
    }

    public SoundEvent getPaintSound() {
        return SoundEvents.GENERIC_SPLASH;
    }
}
