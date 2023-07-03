package dev.JustRed23.paint.items;

import dev.JustRed23.paint.blocks.PaintBucketBlock;
import dev.JustRed23.paint.items.template.CreativeGetterItem;
import dev.JustRed23.paint.items.template.PaintControlsBlock;
import dev.JustRed23.paint.utils.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
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

    public @NotNull ItemStack getCreativeInstance() {
        final ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("uses", initialUsages);
        stack.getOrCreateTag().putInt("maxUses", maxUsages);
        stack.getOrCreateTag().putInt("paintColor", 0xFFFFFF);
        return stack;
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        components.add(Component.translatable("tooltips.paint.uses", stack.getOrCreateTag().getInt("uses"), maxUsages).withStyle(ChatFormatting.GRAY));
        if (stack.hasTag() && stack.getTag().contains("paintColor"))
            components.add(Component.translatable("tooltips.paint.color", ColorUtils.rgbToHex(getColor(stack)))
                    .withStyle(ChatFormatting.GRAY));
    }

    public int getColor(ItemStack stack) {
        return stack.getOrCreateTag().getInt("paintColor");
    }
}
