package dev.JustRed23.paint.providers;

import dev.JustRed23.paint.ModRecipeSerializers;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SpecialRecipeBuilder;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class PaintRecipesProvider extends RecipeProvider {

    public PaintRecipesProvider(PackOutput p_248933_) {
        super(p_248933_);
    }

    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ModRecipeSerializers.getAll().forEach(serializer -> buildModded(consumer, serializer));
    }

    private void buildModded(@NotNull Consumer<FinishedRecipe> consumer, RegistryObject<RecipeSerializer<?>> serializer) {
        final RecipeSerializer<? extends CustomRecipe> recipe;
        try {
            recipe = (RecipeSerializer<? extends CustomRecipe>) serializer.get();
        } catch (ClassCastException cce) {
            throw new RuntimeException("Failed to cast recipe serializer " + serializer.getId().toString() + " to CustomRecipe", cce);
        }
        SpecialRecipeBuilder.special(recipe).save(consumer, serializer.getId().toString());
    }
}
