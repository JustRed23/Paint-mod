package dev.JustRed23.paint.recipes.glass;

import dev.JustRed23.paint.ModBlocks;
import dev.JustRed23.paint.ModItems;
import dev.JustRed23.paint.ModRecipeSerializers;
import dev.JustRed23.paint.items.PaintedBlockItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import static dev.JustRed23.paint.utils.RecipeUtils.*;

public class PaintedGlassPaneRecipe extends CustomRecipe {

    public PaintedGlassPaneRecipe(ResourceLocation loc, CraftingBookCategory category) {
        super(loc, category);
    }

    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        if (!canCraftInDimensions(container.getWidth(), container.getHeight()))
            return false;

        final ItemStack[] slots = getAllSlots(container);

        if (isPane(slots[0], slots[1], slots[2], slots[3], slots[4], slots[5]) && allAir(slots[6], slots[7], slots[8])) // Top recipe
            return allColorsMatch(slots[0], slots[1], slots[2], slots[3], slots[4], slots[5]);

        if (isPane(slots[3], slots[4], slots[5], slots[6], slots[7], slots[8]) && allAir(slots[0], slots[1], slots[2])) // Bottom recipe
            return allColorsMatch(slots[3], slots[4], slots[5], slots[6], slots[7], slots[8]);

        return false;
    }

    private boolean isPane(ItemStack... stacks) {
        for (ItemStack stack : stacks)
            if (!(stack.getItem() instanceof PaintedBlockItem item && item.getBlock().equals(ModBlocks.PAINTED_GLASS.get())))
                return false;
        return true;
    }

    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess access) {
        ItemStack slot4 = container.getItem(3);

        if (slot4.isEmpty()) // There should always be an item here
            return ItemStack.EMPTY;

        final int color = slot4.getTag().getInt("paintColor");
        final ItemStack result = ModItems.get("painted_glass_pane").get().getDefaultInstance();
        result.getTag().putInt("paintColor", color);
        return result.copyWithCount(6);
    }

    public boolean canCraftInDimensions(int gridWidth, int gridHeight) {
        return gridWidth * gridHeight == 9;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PAINTED_GLASS_PANE.get();
    }
}
