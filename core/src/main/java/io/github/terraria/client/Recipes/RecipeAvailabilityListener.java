package io.github.sandboxGame.client.Recipes;

import io.github.sandboxGame.logic.crafting.Recipe;

public interface RecipeAvailabilityListener {
    void onChange(Recipe recipe, boolean available);
}
