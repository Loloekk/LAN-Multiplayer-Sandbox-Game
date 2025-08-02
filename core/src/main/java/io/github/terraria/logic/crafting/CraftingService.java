package io.github.terraria.logic.crafting;

import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.crafting.station.CraftingStation;
import io.github.terraria.logic.crafting.station.StationType;

import java.util.Map;

public class CraftingService {
    private RecipeRepoImpl repo;
    private Map<StationType, CraftingStation> stations;

    boolean canCraft(Recipe recipe, ItemHolder inventory) {
        StationType type = recipe.getStation();
        CraftingStation station = stations.get(type);
        return station.canCraft(recipe, inventory);
    }

    void craft(Recipe recipe, ItemHolder inventory) {
        if (!canCraft(recipe, inventory)) {
            return; // or throw exception?
        }
        stations.get(recipe.getStation()).craft(recipe, inventory);
    }
}
