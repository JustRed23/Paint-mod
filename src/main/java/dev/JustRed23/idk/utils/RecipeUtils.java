package dev.JustRed23.idk.utils;

import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RecipeUtils {

    public static ItemStack[] getAllSlots(CraftingContainer container) {
        ItemStack[] slots = new ItemStack[container.getContainerSize()];
        for (int i = 0; i < container.getContainerSize(); i++)
            slots[i] = container.getItem(i);

        return slots;
    }

    public static boolean allColorsMatch(ItemStack... stacks) {
        List<Integer> colors = new ArrayList<>();
        Arrays.stream(stacks).forEach(stack -> colors.add(stack.hasTag() ? stack.getTag().getInt("paintColor") : -1));
        if (colors.contains(-1))
            return false;

        for (int i = 0; i < colors.size() - 1; i++) {
            if (!colors.get(i).equals(colors.get(i + 1)))
                return false;
        }
        return true;
    }

    public static boolean allAir(ItemStack... items) {
        for (ItemStack item : items)
            if (!item.isEmpty())
                return false;

        return true;
    }
}
