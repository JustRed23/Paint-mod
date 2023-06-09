package dev.JustRed23.idk.utils;

import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class RecipeUtils {

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
}
