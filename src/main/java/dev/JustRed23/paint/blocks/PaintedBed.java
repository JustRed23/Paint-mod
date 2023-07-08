package dev.JustRed23.paint.blocks;

import dev.JustRed23.paint.ModBlockEntities;
import dev.JustRed23.paint.ModItems;
import dev.JustRed23.paint.blocks.blockentities.PaintedBlockEntity;
import dev.JustRed23.paint.items.PaintedBlockItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PaintedBed extends BedBlock {

    public PaintedBed() {
        super(DyeColor.WHITE, Properties.copy(Blocks.WHITE_BED));
    }

    @Override
    public @NotNull InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hitResult) {
        if (level.isClientSide) return InteractionResult.CONSUME;

        if (player.getItemInHand(hand).is(ModItems.PAINTBRUSH.get()))
            return player.getItemInHand(hand).use(level, player, hand).getResult();

        return super.use(state, level, pos, player, hand, hitResult);
    }

    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity entity, @NotNull ItemStack stack) {
        if (!level.isClientSide) {
            BlockPos blockpos = pos.relative(state.getValue(FACING));
            level.setBlock(blockpos, state.setValue(PART, BedPart.HEAD), 3);
            level.blockUpdated(pos, Blocks.AIR);
            state.updateNeighbourShapes(level, pos, 3);

            updateColor(level, pos, stack);
            updateColor(level, blockpos, stack);
        }

    }

    private void updateColor(Level level, BlockPos pos, ItemStack stack) {
        if (stack.getItem() instanceof PaintedBlockItem) {
            PaintedBlockEntity blockEntity = (PaintedBlockEntity) level.getBlockEntity(pos);
            if (stack.hasTag() && stack.getTag().contains("paintColor"))
                blockEntity.setColor(stack.getTag().getInt("paintColor"));
        }
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = super.getCloneItemStack(state, target, level, pos, player);
        stack.getOrCreateTag().putInt("paintColor", ((PaintedBlockEntity) level.getBlockEntity(pos)).getColor());
        return stack;
    }

    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new PaintedBlockEntity(ModBlockEntities.PAINTED_BED_ENTITY.get(), pos, state);
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
