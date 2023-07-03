package dev.JustRed23.paint.items.template;

import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

class ControlUtils {

    static void refill(@NotNull ItemStack stack, int maxUsages) {
        if (stack.hasTag() && stack.getTag().contains("uses"))
            stack.getTag().putInt("uses", maxUsages);
        else stack.getOrCreateTag().putInt("uses", maxUsages);
    }

    static void deplete(@NotNull ItemStack stack, int maxUsages) {
        if (stack.hasTag() && stack.getTag().contains("uses")) {
            int uses = stack.getTag().getInt("uses");
            stack.getTag().putInt("uses", uses - 1);
        } else stack.getOrCreateTag().putInt("uses", maxUsages);
    }

    static void depleteAll(@NotNull ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("uses"))
            stack.getTag().putInt("uses", 0);
    }

    static boolean canUse(@NotNull ItemStack stack, int maxUsages) {
        if (stack.hasTag() && stack.getTag().contains("uses"))
            return stack.getTag().getInt("uses") > 0;
        else stack.getOrCreateTag().putInt("uses", maxUsages);
        return false;
    }
}
