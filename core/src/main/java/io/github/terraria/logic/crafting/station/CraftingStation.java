package io.github.terraria.logic.crafting.station;

import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.crafting.Recipe;

import java.util.List;

public interface CraftingStation {
    StationType getStationType();
    boolean canCraft(Recipe recipe, ItemHolder inventory);
    boolean craft(Recipe recipe, ItemHolder inventory);
}
