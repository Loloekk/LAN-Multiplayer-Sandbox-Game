package io.github.sandboxGame.logic.crafting;

import io.github.sandboxGame.common.StationType;
import io.github.sandboxGame.logic.building.Block;
import io.github.sandboxGame.logic.equipment.ItemHolder;
import io.github.sandboxGame.logic.crafting.station.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CraftingService {
    private final RecipeRepoImpl repo;
    private final CraftingStationRegistry stationRegistry;
    private final StationTypeMap stationTypeMap;

    public CraftingService(RecipeRepoImpl repo, StationTypeMap stationTypeMap, CraftingStationRegistry stationRegistry) {
        this.repo = repo;
        this.stationTypeMap = stationTypeMap;
        this.stationRegistry = stationRegistry;
    }

    public boolean canCraft(Recipe recipe, ItemHolder inventory) {
        StationType type = recipe.station();
        CraftingStation station = stationRegistry.get(type);
        return station.canCraft(recipe, inventory);
    }

    public List<Recipe> getAvailableRecipes(StationType type, ItemHolder inventory) {
        List<Recipe> recipes = new ArrayList<>();
        for (Recipe recipe : repo.findByStation(type)) {
            if (canCraft(recipe, inventory)) {
                recipes.add(recipe);
            }
        }
        return recipes;
    }

    public boolean craft(Recipe recipe, ItemHolder inventory) {
        if (!canCraft(recipe, inventory)) {
            return false;
        }
        stationRegistry.get(recipe.station()).craft(recipe, inventory);
        return true;
    }

    public StationType getStationType(Block block) {
        for (var entry : stationRegistry.all().entrySet()) {
            var key =  entry.getKey();
            if (isCorrectStation(block, key)) {
                return key;
            }
        }
        return null;
    }

    public boolean isCorrectStation(Block block, StationType required) {
        StationType station = stationTypeMap.get(block);
        return station == required;
    }

    public Recipe getById(int id) {
        return repo.getById(id);
    }
}
