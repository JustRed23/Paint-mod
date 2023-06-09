package dev.JustRed23.idk.blocks.blockentities.template;

import dev.JustRed23.idk.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class ColoredBlockEntity extends BlockEntity {

    protected CompoundTag tag;

    protected ColoredBlockEntity(BlockEntityType<?> p_155228_, BlockPos p_155229_, BlockState p_155230_) {
        super(p_155228_, p_155229_, p_155230_);
    }

    protected void saveAdditional(@NotNull CompoundTag tag) {
        super.saveAdditional(tag);

        if (this.tag != null)
            tag.merge(this.tag);
    }

    public void load(@NotNull CompoundTag tag) {
        super.load(tag);
        sync(tag);

        if (level != null && level.isClientSide)
            level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 3);
    }

    protected void sync(CompoundTag tag) {
        if (tag == null)
            return;

        this.tag = tag;
        if (tag.contains("color"))
            setColor(tag.getInt("color"));
    }

    public final void setColor(int color) {
        if (color == -1)
            return;
        getOrCreate().putInt("color", color);
        setChanged();
    }

    public void setChanged() {
        super.setChanged();

        if (level != null && !level.isClientSide)
            ModBlockEntities.sync(this);
    }

    public final int getColor() {
        return getOrCreate().getInt("color") == 0 ? 0xFFFFFF : getOrCreate().getInt("color");
    }

    protected final CompoundTag getOrCreate() {
        if (tag == null)
            tag = new CompoundTag();
        return tag;
    }

    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public @NotNull CompoundTag getUpdateTag() {
        return this.saveWithoutMetadata();
    }
}
