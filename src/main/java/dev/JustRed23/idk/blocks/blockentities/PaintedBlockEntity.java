package dev.JustRed23.idk.blocks.blockentities;

import dev.JustRed23.idk.ModBlockEntities;
import dev.JustRed23.idk.blocks.blockentities.template.ColoredBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.state.BlockState;

public class PaintedBlockEntity extends ColoredBlockEntity {

    public PaintedBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.PAINTED_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }

    public SoundEvent getPaintSound() {
        return SoundEvents.GENERIC_SPLASH;
    }
}
