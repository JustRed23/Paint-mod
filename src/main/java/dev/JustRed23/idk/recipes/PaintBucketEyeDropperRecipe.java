package dev.JustRed23.idk.recipes;

import dev.JustRed23.idk.ModRecipeSerializers;
import dev.JustRed23.idk.items.EyeDropperItem;
import dev.JustRed23.idk.items.PaintBucketItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class PaintBucketEyeDropperRecipe extends CustomRecipe {

    public PaintBucketEyeDropperRecipe(ResourceLocation loc, CraftingBookCategory category) {
        super(loc, category);
    }

    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        if (!canCraftInDimensions(container.getWidth(), container.getHeight()))
            return false;

        ItemStack bucket = ItemStack.EMPTY;
        ItemStack eyeDropper = ItemStack.EMPTY;

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() instanceof PaintBucketItem) {
                    if (!bucket.isEmpty())
                        return false;

                    bucket = itemstack;
                } else if (itemstack.getItem() instanceof EyeDropperItem eyeDropperItem) {
                    if (!eyeDropper.isEmpty())
                        return false;

                    if (eyeDropperItem.hasColor(itemstack))
                        eyeDropper = itemstack;
                }
            }
        }

        if (bucket.isEmpty() || eyeDropper.isEmpty())
            return false;

        PaintBucketItem bucketItem = (PaintBucketItem) bucket.getItem();
        EyeDropperItem eyeDropperItem = (EyeDropperItem) eyeDropper.getItem();
        return bucketItem.canUse(bucket) && (bucketItem.getColor(bucket) != eyeDropperItem.getColor(eyeDropper));
    }

    public @NotNull NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> nonnulllist = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);

        for (int i = 0; i < nonnulllist.size(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (itemstack.getItem().hasCraftingRemainingItem())
                nonnulllist.set(i, new ItemStack(itemstack.getItem().getCraftingRemainingItem()));
            else if (itemstack.getItem() instanceof EyeDropperItem)
                nonnulllist.set(i, itemstack.copy());
        }

        return nonnulllist;
    }

    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess access) {
        ItemStack bucket = ItemStack.EMPTY;
        ItemStack eyeDropper = ItemStack.EMPTY;

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() instanceof PaintBucketItem) {
                    if (!bucket.isEmpty())
                        return ItemStack.EMPTY;

                    bucket = itemstack;
                } else if (itemstack.getItem() instanceof EyeDropperItem) {
                    if (!eyeDropper.isEmpty())
                        return ItemStack.EMPTY;

                    eyeDropper = itemstack;
                }
            }
        }

        ItemStack result = bucket.copy();
        result.setCount(1);
        result.getOrCreateTag().putInt("paintColor", eyeDropper.getOrCreateTag().getInt("paintColor"));
        return result;
    }

    public boolean canCraftInDimensions(int gridWidth, int gridHeight) {
        return gridWidth * gridHeight >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PAINT_BUCKET_EYEDROPPER.get();
    }
}
