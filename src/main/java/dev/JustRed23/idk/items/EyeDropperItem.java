package dev.JustRed23.idk.items;

import dev.JustRed23.idk.items.template.CreativeGetterItem;
import dev.JustRed23.idk.utils.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EyeDropperItem extends Item implements CreativeGetterItem {

    public EyeDropperItem() {
        super(new Item.Properties().stacksTo(1).setNoRepair());
    }

    public @NotNull ItemStack getDefaultInstance() {
        return new ItemStack(this);
    }

    public @NotNull ItemStack getCreativeInstance() {
        ItemStack stack = getDefaultInstance();
        stack.getOrCreateTag().putInt("paintColor", 0xFFFFFF);
        return stack;
    }

    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> components, TooltipFlag flag) {
        if (stack.hasTag() && stack.getTag().contains("paintColor"))
            components.add(Component.translatable("tooltips.idk.color", ColorUtils.rgbToHex(stack.getOrCreateTag().getInt("paintColor")))
                    .withStyle(ChatFormatting.GRAY));
    }

    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
        //TODO: get the map color of the block, then set the color of the item to that
        // Craft this together with the paint bucket to get the color of the block
        // EXTRA: make it possible to right click entities and get their color
        return super.use(level, player, hand);
    }

    public boolean hasColor(ItemStack stack) {
        return stack.getOrCreateTag().contains("paintColor");
    }

    public int getColor(ItemStack stack) {
        return stack.getOrCreateTag().getInt("paintColor");
    }
}
