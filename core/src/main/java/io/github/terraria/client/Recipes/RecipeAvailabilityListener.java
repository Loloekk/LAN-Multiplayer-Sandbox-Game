package io.github.terraria.client.Recipes;

import io.github.terraria.logic.crafting.Recipe;

public interface RecipeAvailabilityListener {
    void onChange(Recipe recipe, boolean available);
}
