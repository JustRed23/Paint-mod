package dev.JustRed23.paint.mixin;

import dev.JustRed23.paint.blocks.paintedblockvariants.PaintedGlass;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.HalfTransparentBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(HalfTransparentBlock.class)
public class MixinHalfTransparentBlock {

    /**
     * @author JustRed23
     * @reason Makes Painted Glass render correctly
     */
    @Overwrite
    public boolean skipRendering(BlockState state, BlockState neighbour, Direction direction) {
        if (state.getBlock() instanceof AbstractGlassBlock && neighbour.getBlock() instanceof PaintedGlass)
            return true;

        return neighbour.getBlock() instanceof HalfTransparentBlock;
    }
}
