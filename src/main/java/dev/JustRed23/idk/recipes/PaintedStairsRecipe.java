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

import static dev.JustRed23.idk.utils.RecipeUtils.allAir;
import static dev.JustRed23.idk.utils.RecipeUtils.allColorsMatch;

public class PaintedStairsRecipe extends CustomRecipe {

    public PaintedStairsRecipe(ResourceLocation loc, CraftingBookCategory category) {
        super(loc, category);
    }

    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        if (!canCraftInDimensions(container.getWidth(), container.getHeight()))
            return false;

        final ItemStack slot1 = container.getItem(0);
        final ItemStack slot2 = container.getItem(1);
        final ItemStack slot3 = container.getItem(2);
        final ItemStack slot4 = container.getItem(3);
        final ItemStack slot5 = container.getItem(4);
        final ItemStack slot6 = container.getItem(5);
        final ItemStack slot7 = container.getItem(6);
        final ItemStack slot8 = container.getItem(7);
        final ItemStack slot9 = container.getItem(8);

        return isPaintedBlock(slot1) &&
                isPaintedBlock(slot4) && isPaintedBlock(slot5) &&
                isPaintedBlock(slot7) && isPaintedBlock(slot8) && isPaintedBlock(slot9) &&
                allAir(slot2, slot3, slot6) &&
                allColorsMatch(slot1, slot4, slot5, slot7, slot8, slot9);
    }

    private boolean isPaintedBlock(ItemStack stack) {
        return stack.getItem() instanceof PaintedBlockItem && stack.is(ModItems.get("painted_block").get());
    }

    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess access) {
        final int color = container.getItem(0).getTag().getInt("paintColor");
        final ItemStack result = ModItems.get("painted_stairs").get().getDefaultInstance();
        result.getTag().putInt("paintColor", color);
        return result.copyWithCount(4);
    }

    public boolean canCraftInDimensions(int gridWidth, int gridHeight) {
        return gridWidth * gridHeight == 9;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PAINTED_STAIRS.get();
    }
}
