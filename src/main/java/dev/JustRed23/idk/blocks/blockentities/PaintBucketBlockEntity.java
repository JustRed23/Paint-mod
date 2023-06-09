package dev.JustRed23.idk.blocks.blockentities;

import dev.JustRed23.idk.ModBlockEntities;
import dev.JustRed23.idk.blocks.PaintBucketBlock;
import dev.JustRed23.idk.blocks.blockentities.template.ColoredBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;

public class PaintBucketBlockEntity extends ColoredBlockEntity {

    public PaintBucketBlockEntity(BlockPos p_155229_, BlockState p_155230_) {
        super(ModBlockEntities.PAINT_BUCKET_BLOCK_ENTITY.get(), p_155229_, p_155230_);
    }

    protected void sync(CompoundTag tag) {
        super.sync(tag);

        if (tag.contains("uses"))
            setUses(tag.getInt("uses"));

        if (tag.contains("maxUses"))
            setMaxUses(tag.getInt("maxUses"));
    }

    public int getUses() {
        return getOrCreate().getInt("uses");
    }

    public void setUses(int uses) {
        if (uses == -1)
            return;
        getOrCreate().putInt("uses", uses);
        setChanged();
    }

    public void setChanged() {
        super.setChanged();

        if (tag.contains("uses")) {
            if (getUses() <= 0)
                level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos()).setValue(PaintBucketBlock.EMPTY, true));
            else if (getUses() > 0)
                level.setBlockAndUpdate(getBlockPos(), level.getBlockState(getBlockPos())
                        .setValue(PaintBucketBlock.EMPTY, false)
                        .setValue(PaintBucketBlock.LEVEL, getUses() / 10)
                );
        }
    }

    public void deplete(int diff) {
        setUses(getUses() - diff);
    }

    public int getMaxUses() {
        return getOrCreate().getInt("maxUses");
    }

    public void setMaxUses(int maxUses) {
        if (maxUses == -1)
            return;
        getOrCreate().putInt("maxUses", maxUses);
        setChanged();
    }
}
