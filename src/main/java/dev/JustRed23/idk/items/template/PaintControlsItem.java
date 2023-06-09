package dev.JustRed23.idk.items.template;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public abstract class PaintControlsItem extends Item {

    protected final int maxUsages;
    protected final int initialUsages;

    public PaintControlsItem(int maxUsages, int initialUsages) {
        super(new Item.Properties().stacksTo(1).setNoRepair());
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
        refill(stack, maxUsages);
    }

    public void refill(ItemStack stack, int amount) {
        ControlUtils.refill(stack, amount);
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

    public int getUses(ItemStack stack) {
        return stack.getOrCreateTag().getInt("uses");
    }

    public int getMaxUses(ItemStack stack) {
        return stack.getOrCreateTag().getInt("maxUses");
    }
}
