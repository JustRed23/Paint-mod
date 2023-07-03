package dev.JustRed23.paint.blocks;

import dev.JustRed23.paint.blocks.blockentities.PaintBucketBlockEntity;
import dev.JustRed23.paint.items.PaintBucketItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class PaintBucketBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty EMPTY = BooleanProperty.create("empty");
    public static final IntegerProperty LEVEL = IntegerProperty.create("level", 1, 10);

    private static final VoxelShape SHAPE = Stream.of(
            //Bucket shape
            Stream.of(
                    Block.box(6, 7, 4, 11, 8, 5),
                    Block.box(4, 7, 6, 5, 8, 11),
                    Block.box(5, 1, 11, 11, 8, 12),
                    Block.box(4, 1, 5, 5, 7, 11),
                    Block.box(11, 1, 5, 12, 8, 11),
                    Block.box(5, 1, 4, 11, 7, 5),
                    Block.box(5, 0, 5, 11, 1, 11)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get(),

            //Paint shape
            Stream.of(
                    Block.box(5, 1, 5, 11, 8, 11),
                    Block.box(4, 7, 5, 5, 8, 6),
                    Block.box(5, 7, 4, 6, 8, 5)
            ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get()
    ).reduce((v1, v2) -> Shapes.join(v1, v2, BooleanOp.OR)).get();

    public PaintBucketBlock() {
        super(BlockBehaviour.Properties.of(Material.DECORATED_POT, MaterialColor.COLOR_GRAY));
    }

    public void setPlacedBy(@NotNull Level level, @NotNull BlockPos pos, @NotNull BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (stack.getItem() instanceof PaintBucketItem paintBucketItem) {
            PaintBucketBlockEntity blockEntity = (PaintBucketBlockEntity) level.getBlockEntity(pos);
            if (stack.hasTag() && stack.getTag().contains("paintColor"))
                blockEntity.setColor(paintBucketItem.getColor(stack));

            if (stack.hasTag() && stack.getTag().contains("uses"))
                blockEntity.setUses(stack.getTag().getInt("uses"));
            if (stack.hasTag() && stack.getTag().contains("maxUses"))
                blockEntity.setMaxUses(stack.getTag().getInt("maxUses"));
        }
    }

    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootContext.Builder builder) {
        ItemStack stack = new ItemStack(this);
        PaintBucketBlockEntity blockEntity = ((PaintBucketBlockEntity) builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY));
        if (blockEntity != null) {
            stack.getOrCreateTag().putInt("paintColor", blockEntity.getColor());
            stack.getOrCreateTag().putInt("uses", blockEntity.getUses());
            stack.getOrCreateTag().putInt("maxUses", blockEntity.getMaxUses());
        }
        return List.of(stack);
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = super.getCloneItemStack(state, target, level, pos, player);
        stack.getOrCreateTag().putInt("paintColor", ((PaintBucketBlockEntity) level.getBlockEntity(pos)).getColor());
        stack.getOrCreateTag().putInt("uses", ((PaintBucketBlockEntity) level.getBlockEntity(pos)).getUses());
        stack.getOrCreateTag().putInt("maxUses", ((PaintBucketBlockEntity) level.getBlockEntity(pos)).getMaxUses());
        return stack;
    }

    @Nullable
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new PaintBucketBlockEntity(pos, state);
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }

    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter getter, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Nullable
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        int uses = context.getItemInHand().getOrCreateTag().getInt("uses");
        return this.defaultBlockState()
                .setValue(FACING, context.getHorizontalDirection().getOpposite())
                .setValue(EMPTY, uses == 0)
                .setValue(LEVEL, uses == 0 ? 0 : uses / 10);
    }

    public @NotNull BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }

    public @NotNull BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
        builder.add(EMPTY);
        builder.add(LEVEL);
    }
}
