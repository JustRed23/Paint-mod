package dev.JustRed23.idk.recipes;

import com.google.common.collect.Lists;
import dev.JustRed23.idk.ModRecipeSerializers;
import dev.JustRed23.idk.items.PaintBucketItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PaintBucketDyeRecipe extends CustomRecipe {

    public PaintBucketDyeRecipe(ResourceLocation loc, CraftingBookCategory category) {
        super(loc, category);
    }

    public boolean matches(@NotNull CraftingContainer container, @NotNull Level level) {
        ItemStack bucket = ItemStack.EMPTY;
        List<ItemStack> dyes = Lists.newArrayList();

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() instanceof PaintBucketItem) {
                    if (!bucket.isEmpty())
                        return false;

                    bucket = itemstack;
                } else {
                    if (!(itemstack.getItem() instanceof DyeItem))
                        return false;

                    dyes.add(itemstack);
                }
            }
        }

        return !bucket.isEmpty() && !dyes.isEmpty();
    }

    public @NotNull ItemStack assemble(@NotNull CraftingContainer container, @NotNull RegistryAccess access) {
        ItemStack bucket = ItemStack.EMPTY;
        List<ItemStack> dyes = Lists.newArrayList();

        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack = container.getItem(i);
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() instanceof PaintBucketItem) {
                    if (!bucket.isEmpty())
                        return ItemStack.EMPTY;

                    bucket = itemstack;
                } else {
                    if (!(itemstack.getItem() instanceof DyeItem))
                        return ItemStack.EMPTY;

                    dyes.add(itemstack);
                }
            }
        }

        if (!bucket.isEmpty() && !dyes.isEmpty())
            if (((PaintBucketItem) bucket.getItem()).canUse(bucket))
                return PaintBucketItem.dyeBucket(bucket, dyes);

        return ItemStack.EMPTY;
    }

    public boolean canCraftInDimensions(int gridWidth, int gridHeight) {
        return gridWidth * gridHeight >= 2;
    }

    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.PAINT_BUCKET_DYE.get();
    }
}
