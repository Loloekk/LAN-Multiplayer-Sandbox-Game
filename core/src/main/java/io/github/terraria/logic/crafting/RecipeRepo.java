package io.github.sandboxGame.logic.crafting;

import io.github.sandboxGame.common.StationType;

import java.util.List;

public interface RecipeRepo {
    List<Recipe> getAll();
    Recipe getById(int id);
    List<Recipe> findByStation(StationType station);
}
