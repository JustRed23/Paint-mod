package dev.JustRed23.idk.recipes;

import dev.JustRed23.idk.ModItems;
import dev.JustRed23.idk.ModRecipeSerializers;
import dev.JustRed23.idk.items.PaintedBlockItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static dev.JustRed23.idk.utils.RecipeUtils.allColorsMatch;
import static dev.JustRed23.idk.utils.RecipeUtils.allAir;

public class PaintedSlabRecipe extends CustomRecipe {

    public PaintedSlabRecipe(ResourceLocation loc, CraftingBookCategory category) {
        super(loc, category);
    }

    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        ItemStack slot1 = container.getItem(0);
        ItemStack slot2 = container.getItem(1);
        ItemStack slot3 = container.getItem(2);
        ItemStack slot4 = container.getItem(3);
        ItemStack slot5 = container.getItem(4);
        ItemStack slot6 = container.getItem(5);
        ItemStack slot7 = container.getItem(6);
        ItemStack slot8 = container.getItem(7);
        ItemStack slot9 = container.getItem(8);

        if (isSlab(slot1, slot2, slot3) && allAir(slot4, slot5, slot6, slot7, slot8, slot9))
            return allColorsMatch(slot1, slot2, slot3);

        if (isSlab(slot4, slot5, slot6) && allAir(slot1, slot2, slot3, slot7, slot8, slot9))
            return allColorsMatch(slot4, slot5, slot6);

        if (isSlab(slot7, slot8, slot9) && allAir(slot1, slot2, slot3, slot4, slot5, slot6))
            return allColorsMatch(slot7, slot8, slot9);

        return false;
    }

    private boolean isSlab(ItemStack item1, ItemStack item2, ItemStack item3) {
        if (item1.getItem() instanceof PaintedBlockItem && item2.getItem() instanceof PaintedBlockItem && item3.getItem() instanceof PaintedBlockItem)
            return true;
        return false;
    }

    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess access) {
        ItemStack slot1 = container.getItem(0);
        ItemStack slot4 = container.getItem(3);
        ItemStack slot7 = container.getItem(6);

        ItemStack nonEmpty = slot1.isEmpty() ? slot4.isEmpty() ? slot7 : slot4 : slot1;
        if (nonEmpty.isEmpty())
            return ItemStack.EMPTY;

        final int color = nonEmpty.getTag().getInt("paintColor");
        final ItemStack result = ModItems.get("painted_slab").get().getDefaultInstance();
        result.getTag().putInt("paintColor", color);
        return result.copyWithCount(6);
    }

    public boolean canCraftInDimensions(int gridWidth, int gridHeight) {
        return gridWidth * gridHeight == 9;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PAINTED_SLAB.get();
    }
}
