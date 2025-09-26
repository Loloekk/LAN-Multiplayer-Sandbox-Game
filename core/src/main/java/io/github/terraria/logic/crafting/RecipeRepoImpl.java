package io.github.terraria.logic.crafting;

import io.github.terraria.logic.equipment.ItemRegistry;
import io.github.terraria.loading.RecordLoader;
import io.github.terraria.common.StationType;

import java.util.List;

public class RecipeRepoImpl implements RecipeRepo {
    final private List<Recipe> all;

    public RecipeRepoImpl(List<Recipe> all) {
        this.all = all;
    }
    @Override
    public List<Recipe> getAll() {
        return all;
    }

    @Override
    public Recipe getById(int id) {
        return all.stream().filter(r -> r.recipeId() == id).findFirst().orElse(null);
    }

    @Override
    public List<Recipe> findByStation(StationType station) {
        return all.stream().filter(r -> r.station() == station).toList();
    }
}
