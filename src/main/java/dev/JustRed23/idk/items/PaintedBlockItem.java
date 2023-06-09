package dev.JustRed23.idk.items;

import dev.JustRed23.idk.blocks.PaintedBlock;
import dev.JustRed23.idk.items.template.CreativeGetterItem;
import dev.JustRed23.idk.utils.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PaintedBlockItem extends BlockItem implements CreativeGetterItem {

    public PaintedBlockItem(PaintedBlock block) {
        super(block, new Properties());
    }

    public @NotNull ItemStack getCreativeInstance() {
        ItemStack stack = new ItemStack(this);
        stack.getOrCreateTag().putInt("paintColor", 0xFFFFFF);
        return stack;
    }

    public void appendHoverText(@NotNull ItemStack stack, Level level, @NotNull List<Component> components, @NotNull TooltipFlag flag) {
        if (stack.hasTag() && stack.getTag().contains("paintColor"))
            components.add(Component.translatable("tooltips.idk.color", ColorUtils.rgbToHex(stack.getOrCreateTag().getInt("paintColor")))
                    .withStyle(ChatFormatting.GRAY));
    }
}
