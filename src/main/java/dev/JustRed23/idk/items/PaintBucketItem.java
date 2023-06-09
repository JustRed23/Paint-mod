package dev.JustRed23.idk.items;

import dev.JustRed23.idk.blocks.PaintBucketBlock;
import dev.JustRed23.idk.items.template.CreativeGetterItem;
import dev.JustRed23.idk.items.template.PaintControlsBlock;
import dev.JustRed23.idk.utils.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PaintBucketItem extends PaintControlsBlock implements CreativeGetterItem {

    public PaintBucketItem(PaintBucketBlock block) {
        this(block, 100, 100);
    }

    public PaintBucketItem(PaintBucketBlock block, int maxUsages, int initialUsages) {
        super(block, maxUsages, initialUsages);
    }

    public static ItemStack dyeBucket(ItemStack bucket, List<ItemStack> dyes) {
        if (bucket.getItem() instanceof PaintBucketItem) {
            ItemStack newBucket = bucket.copy();
            CompoundTag tag = newBucket.getOrCreateTag();
            int uses = tag.getInt("uses");
            int color = tag.getInt("paintColor");

            if (uses > 0) {
                for (ItemStack dye : dyes) {
                    if (dye.getItem() instanceof DyeItem dyeItem) {
                        int dyeColor = dyeItem.getDyeColor().getMaterialColor().col;
                        if (dyeColor != color)
                            color = ColorUtils.mixColors(color, dyeColor);
                    }
                }
            }

            tag.putInt("paintColor", color);
            return newBucket;
        }
        return ItemStack.EMPTY;
    }

    public @NotNull ItemStack getCreativeInstance() {
        final ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("uses", initialUsages);
        stack.getOrCreateTag().putInt("maxUses", maxUsages);
        stack.getOrCreateTag().putInt("paintColor", 0xFFFFFF);
        return stack;
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("item.idk.paint_bucket.tooltip.uses", stack.getOrCreateTag().getInt("uses"), maxUsages).withStyle(ChatFormatting.GRAY));
        if (stack.hasTag() && stack.getTag().contains("paintColor"))
            components.add(Component.translatable("item.idk.paint_bucket.tooltip.color", ColorUtils.rgbToHex(stack.getOrCreateTag().getInt("paintColor")))
                    .withStyle(ChatFormatting.GRAY));
    }
}
