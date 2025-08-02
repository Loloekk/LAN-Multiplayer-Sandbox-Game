package io.github.terraria.logic.crafting.station;

import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.crafting.Recipe;

import java.util.List;

public class WorkBenchStation implements CraftingStation {

    @Override
    public StationType getStationType() {
        return null;
    }

    @Override
    public List<Recipe> getAvailableRecipes() {
        return List.of();
    }

    @Override
    public boolean canCraft(Recipe recipe, ItemHolder inventory) {
        return false;
    }

    @Override
    public void craft(Recipe recipe, ItemHolder inventory) {

    }
}
