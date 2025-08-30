package io.github.terraria.logic.crafting;

import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.crafting.station.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftingService {
    private final RecipeRepoImpl repo;
    private final Map<StationType, CraftingStation> stations;

    public CraftingService(RecipeRepoImpl repo) {
        this.repo = repo;
        this.stations = new HashMap<>();
        this.stations.put(StationType.WORKBENCH, new WorkBenchStation());
        this.stations.put(StationType.FURNACE, new FurnaceStation());
        this.stations.put(StationType.ANVIL, new AnvilStation());
    }

    public boolean canCraft(Recipe recipe, ItemHolder inventory) {
        StationType type = recipe.getStation();
        CraftingStation station = stations.get(type);
        return station.canCraft(recipe, inventory);
    }

    public List<Recipe> getAvailableRecipes(StationType type) {
        return repo.findByStation(type);
    }

    public boolean craft(Recipe recipe, ItemHolder inventory) {
        if (!canCraft(recipe, inventory)) {
            return false;
        }
        stations.get(recipe.getStation()).craft(recipe, inventory);
        return true;
    }
}
