package io.github.terraria.logic.crafting;

import io.github.terraria.logic.actions.GameState;
import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.equipment.ItemHolder;
import io.github.terraria.logic.crafting.station.*;
import io.github.terraria.logic.players.PhysicalPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CraftingService {
    private final RecipeRepoImpl repo;
    private final Map<StationType, CraftingStation> stations;
    private final StationTypeMap stationTypeMap;

    public CraftingService(RecipeRepoImpl repo, StationTypeMap stationTypeMap) {
        this.repo = repo;
        this.stationTypeMap = stationTypeMap;
        this.stations = Map.of(
            StationType.WORKBENCH, new WorkBenchStation(),
            StationType.FURNACE, new FurnaceStation(),
            StationType.ANVIL, new AnvilStation(),
            StationType.INVENTORY, new InventoryStation()
        );
    }

    public boolean canCraft(Recipe recipe, ItemHolder inventory) {
        StationType type = recipe.station();
        CraftingStation station = stations.get(type);
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
        stations.get(recipe.station()).craft(recipe, inventory);
        return true;
    }

    public StationType getStationType(Block block) {
        for (var entry : stations.entrySet()) {
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
}
