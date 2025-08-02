package io.github.terraria.logic.crafting;

import io.github.terraria.logic.crafting.station.StationType;

import java.util.List;

public class RecipeRepoImpl implements RecipeRepo {
    final private List<Recipe> all = json.parse(...); // takes all recipes from db
    @Override
    public List<Recipe> getAll() {
        return List.of();
    }

    @Override
    public Recipe getById(int id) {
        return null;
    }

    @Override
    public List<Recipe> findByStation(StationType station) {
        return List.of();
    }
}
