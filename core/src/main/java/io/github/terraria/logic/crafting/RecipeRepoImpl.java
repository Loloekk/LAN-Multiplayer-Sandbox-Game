package io.github.terraria.logic.crafting;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import io.github.terraria.logic.crafting.station.StationType;

import java.util.ArrayList;
import java.util.List;

public class RecipeRepoImpl implements RecipeRepo {
    final private List<Recipe> all;
    public RecipeRepoImpl() {
        Json json = new Json();
        all = json.fromJson(ArrayList.class, Recipe.class, Gdx.files.internal("crafting/recipes.json"));
    }
    @Override
    public List<Recipe> getAll() {
        return all;
    }

    @Override
    public Recipe getById(int id) {
        return all.stream().filter(r -> r.getRecipeId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Recipe> findByStation(StationType station) {
        return all.stream().filter(r -> r.getStation() == station).toList();
    }
}
