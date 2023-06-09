package dev.JustRed23.idk.items.template;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public abstract class PaintControlsBlock extends BlockItem {

    protected final int maxUsages;
    protected final int initialUsages;

    public PaintControlsBlock(Block block, int maxUsages, int initialUsages) {
        super(block, new Item.Properties().stacksTo(1).setNoRepair());
        this.maxUsages = maxUsages;
        this.initialUsages = initialUsages;
    }

    public @NotNull ItemStack getDefaultInstance() {
        final ItemStack defaultInstance = new ItemStack(this);
        final CompoundTag tag = new CompoundTag();
        tag.putInt("uses", initialUsages);
        defaultInstance.setTag(tag);
        return defaultInstance;
    }

    public void refill(ItemStack stack) {
        ControlUtils.refill(stack, maxUsages);
    }

    public void deplete(ItemStack stack) {
        ControlUtils.deplete(stack, maxUsages);
    }

    public void depleteAll(ItemStack stack) {
        ControlUtils.depleteAll(stack);
    }

    public boolean canUse(ItemStack stack) {
        return ControlUtils.canUse(stack, maxUsages);
    }
}
