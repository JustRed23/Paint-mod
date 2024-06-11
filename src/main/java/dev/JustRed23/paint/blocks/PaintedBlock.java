package dev.JustRed23.paint.blocks;

import dev.JustRed23.paint.ModBlockEntities;
import dev.JustRed23.paint.blocks.blockentities.PaintedBlockEntity;
import dev.JustRed23.paint.items.PaintedBlockItem;
import dev.JustRed23.paint.particle.paint.PaintParticleData;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaintedBlock extends BaseEntityBlock {

    public PaintedBlock() {
        this(Block.Properties.copy(Blocks.WHITE_TERRACOTTA).noParticlesOnBreak());
    }

    public PaintedBlock(Block.Properties properties) {
        super(properties);
    }

    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        if (stack.getItem() instanceof PaintedBlockItem) {
            PaintedBlockEntity blockEntity = (PaintedBlockEntity) level.getBlockEntity(pos);
            if (stack.hasTag() && stack.getTag().contains("paintColor"))
                blockEntity.setColor(stack.getTag().getInt("paintColor"));
        }
    }

    public int getCount(BlockState state) {
        return 1;
    }

    public @NotNull List<ItemStack> getDrops(@NotNull BlockState state, LootParams.Builder builder) {
        ItemStack stack = new ItemStack(this, getCount(state));
        PaintedBlockEntity blockEntity = ((PaintedBlockEntity) builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY));
        if (blockEntity != null)
            stack.getOrCreateTag().putInt("paintColor", blockEntity.getColor());
        return List.of(stack);
    }

    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos, Player player) {
        ItemStack stack = super.getCloneItemStack(state, target, level, pos, player);
        stack.getOrCreateTag().putInt("paintColor", ((PaintedBlockEntity) level.getBlockEntity(pos)).getColor());
        return stack;
    }

    public boolean addRunningEffects(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide)
            return false;

        if (!(level.getBlockEntity(pos) instanceof PaintedBlockEntity blockEntity))
            return false;

        RandomSource random = level.getRandom();
        BlockState blockState = level.getBlockState(pos);
        int color = blockEntity.getColor();
        level.addParticle(new PaintParticleData(blockState, color).setPos(pos), entity.getX() + (random.nextDouble() - 0.5D) * (double)entity.getBbWidth(), entity.getY() + 0.1D, entity.getZ() + (random.nextDouble() - 0.5D) * (double)entity.getBbWidth(), entity.getDeltaMovement().x * -4.0D, 1.5D, entity.getDeltaMovement().z * -4.0D);
        return true;
    }

    public boolean addLandingEffects(BlockState state1, ServerLevel level, BlockPos pos, BlockState state2, LivingEntity entity, int numberOfParticles) {
        if (!(level.getBlockEntity(pos) instanceof PaintedBlockEntity blockEntity))
            return false;

        BlockState blockState = level.getBlockState(pos);
        int color = blockEntity.getColor();
        level.sendParticles(new PaintParticleData(blockState, color).setPos(pos), entity.getX(), entity.getY(), entity.getZ(), numberOfParticles, 0.0D, 0.0D, 0.0D, (double)0.15F);
        return true;
    }

    @Nullable
    public BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
        return new PaintedBlockEntity(getBlockEntityType(), pos, state);
    }

    public BlockEntityType<PaintedBlockEntity> getBlockEntityType() {
        return ModBlockEntities.PAINTED_BLOCK_ENTITY.get();
    }

    public @NotNull RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.MODEL;
    }
}
