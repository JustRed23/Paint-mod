package dev.JustRed23.paint.items;

import dev.JustRed23.paint.items.template.CreativeGetterItem;
import dev.JustRed23.paint.utils.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PaintedBlockItem extends BlockItem implements CreativeGetterItem {

    public static ItemStack dyeItem(ItemStack item, List<ItemStack> dyes, int count) {
        if (item.getItem() instanceof PaintBucketItem) {
            ItemStack newBucket = item.copyWithCount(1);
            CompoundTag tag = newBucket.getOrCreateTag();
            int uses = tag.getInt("uses");
            if (uses > 0)
                setColor(tag, dyes);

            return newBucket;
        } else if (item.getItem() instanceof PaintedBlockItem) {
            ItemStack stack = item.copyWithCount(count);
            CompoundTag tag = stack.getOrCreateTag();
            setColor(tag, dyes);
            return stack;
        }
        return ItemStack.EMPTY;
    }

    public static ItemStack dyeItem(ItemStack item, List<ItemStack> dyes) {
        return dyeItem(item, dyes, 1);
    }

    private static void setColor(CompoundTag tag, List<ItemStack> dyes) {
        int color = tag.getInt("paintColor");

        for (ItemStack dye : dyes) {
            if (dye.getItem() instanceof DyeItem dyeItem) {
                int dyeColor = dyeItem.getDyeColor().getMaterialColor().col;
                if (dyeColor != color)
                    color = ColorUtils.mixColors(color, dyeColor);
            }
        }

        tag.putInt("paintColor", color);
    }

    public PaintedBlockItem(Block block) {
        super(block, new Properties());
    }

    public @NotNull ItemStack getDefaultInstance() {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("paintColor", 0xFFFFFF);
        return stack;
    }

    public @NotNull ItemStack getCreativeInstance() {
        return getDefaultInstance();
    }

    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if (stack.hasTag() && stack.getTag().contains("paintColor"))
            components.add(Component.translatable("tooltips.paint.color", ColorUtils.rgbToHex(stack.getOrCreateTag().getInt("paintColor")))
                    .withStyle(ChatFormatting.GRAY));
    }
}
