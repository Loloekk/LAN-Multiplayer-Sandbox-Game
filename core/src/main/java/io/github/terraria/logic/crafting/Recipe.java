package io.github.terraria.logic.crafting;

import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.crafting.station.StationType;

import java.util.List;

/*
Gets info from "somewhere" (maybe json?) in usable form
 */
public class Recipe {
    private final int recipeId;
    private final List<Ingredient> ingredients;
    private final Ingredient output;
    private final StationType station;

    Recipe(int recipeId, List<Ingredient> ingredients, Ingredient output, StationType station) {
        this.recipeId = recipeId;
        this.ingredients = ingredients;
        this.output = output;
        this.station = station;
    }

    public StationType getStation() { return null; }
    public boolean canCraft(ItemHolder inventory) { return true; }
}
