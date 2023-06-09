package dev.JustRed23.idk;

import dev.JustRed23.idk.recipes.PaintBucketDyeRecipe;
import dev.JustRed23.idk.recipes.PaintBucketEyeDropperRecipe;
import dev.JustRed23.idk.recipes.PaintedSlabRecipe;
import dev.JustRed23.idk.recipes.PaintedStairsRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class ModRecipeSerializers {

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, IDK.MOD_ID);

    static void register(IEventBus bus) {
        RECIPE_SERIALIZERS.register(bus);
    }

    //RECIPE SERIALIZERS
    public static final RegistryObject<RecipeSerializer<PaintBucketDyeRecipe>> PAINT_BUCKET_DYE = RECIPE_SERIALIZERS.register("paint_bucket_dye", () -> new SimpleCraftingRecipeSerializer<>(PaintBucketDyeRecipe::new));
    public static final RegistryObject<RecipeSerializer<PaintBucketEyeDropperRecipe>> PAINT_BUCKET_EYEDROPPER = RECIPE_SERIALIZERS.register("paint_bucket_eyedropper", () -> new SimpleCraftingRecipeSerializer<>(PaintBucketEyeDropperRecipe::new));
    public static final RegistryObject<RecipeSerializer<PaintedSlabRecipe>> PAINTED_SLAB = RECIPE_SERIALIZERS.register("painted_slab", () -> new SimpleCraftingRecipeSerializer<>(PaintedSlabRecipe::new));
    public static final RegistryObject<RecipeSerializer<PaintedStairsRecipe>> PAINTED_STAIRS = RECIPE_SERIALIZERS.register("painted_stairs", () -> new SimpleCraftingRecipeSerializer<>(PaintedStairsRecipe::new));

}
