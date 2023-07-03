package dev.JustRed23.paint.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public final class BlockUtils {

    public static boolean no(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> entityType) {
        return no(state, getter, pos);
    }

    public static boolean no(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }
}
