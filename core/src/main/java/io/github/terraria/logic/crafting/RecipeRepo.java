package io.github.terraria.logic.crafting;

import io.github.terraria.common.StationType;

import java.util.List;

public interface RecipeRepo {
    List<Recipe> getAll();
    Recipe getById(int id);
    List<Recipe> findByStation(StationType station);
}
