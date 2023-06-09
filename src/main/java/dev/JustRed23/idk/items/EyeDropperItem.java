package dev.JustRed23.idk.items;

import dev.JustRed23.idk.blocks.blockentities.template.ColoredBlockEntity;
import dev.JustRed23.idk.items.template.CreativeGetterItem;
import dev.JustRed23.idk.utils.ColorUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
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

    public InteractionResult useOn(UseOnContext context) {
        BlockPos pos = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(pos);

        BlockEntity blockEntity = context.getLevel().getBlockEntity(pos);
        if (blockEntity != null) {
            if (blockEntity instanceof ColoredBlockEntity colored) {
                setColor(context.getItemInHand(), colored.getColor());
                return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
            }
        }

        int color = state.getMapColor(context.getLevel(), pos).col;
        setColor(context.getItemInHand(), color);
        return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
    }

    public boolean hasColor(ItemStack stack) {
        return stack.getOrCreateTag().contains("paintColor");
    }

    public int getColor(ItemStack stack) {
        return stack.getOrCreateTag().getInt("paintColor");
    }

    public void setColor(ItemStack stack, int color) {
        stack.getOrCreateTag().putInt("paintColor", color);
    }
}
