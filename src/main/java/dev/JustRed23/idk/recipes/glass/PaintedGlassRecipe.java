package dev.JustRed23.idk.recipes.glass;

import com.google.common.collect.Lists;
import dev.JustRed23.idk.ModItems;
import dev.JustRed23.idk.ModRecipeSerializers;
import dev.JustRed23.idk.items.PaintedBlockItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PaintedGlassRecipe extends CustomRecipe {

    public PaintedGlassRecipe(ResourceLocation loc, CraftingBookCategory category) {
        super(loc, category);
    }

    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        if (!canCraftInDimensions(container.getWidth(), container.getHeight()))
            return false;

        ItemStack glass = ItemStack.EMPTY;
        List<ItemStack> dyes = Lists.newArrayList();

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.is(Items.GLASS)) {
                    if (!glass.isEmpty())
                        return false;

                    glass = itemstack;
                } else {
                    if (!(itemstack.getItem() instanceof DyeItem))
                        return false;

                    dyes.add(itemstack);
                }
            }
        }

        return !glass.isEmpty() && !dyes.isEmpty();
    }

    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess access) {
        ItemStack glass = ItemStack.EMPTY;
        List<ItemStack> dyes = Lists.newArrayList();

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.is(Items.GLASS)) {
                    if (!glass.isEmpty())
                        return ItemStack.EMPTY;

                    glass = itemstack;
                } else {
                    if (!(itemstack.getItem() instanceof DyeItem))
                        return ItemStack.EMPTY;

                    dyes.add(itemstack);
                }
            }
        }


        if (!glass.isEmpty() && !dyes.isEmpty())
            return PaintedBlockItem.dyeItem(ModItems.get("painted_glass").get().getDefaultInstance(), dyes);

        return ItemStack.EMPTY;
    }

    public boolean canCraftInDimensions(int gridWidth, int gridHeight) {
        return gridWidth * gridHeight >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PAINTED_GLASS.get();
    }
}
