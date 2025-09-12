package io.github.terraria.logic.crafting.station;

import io.github.terraria.logic.equipment.ItemHolder;
import io.github.terraria.logic.crafting.Recipe;

public interface CraftingStation {
    StationType getStationType();
    boolean canCraft(Recipe recipe, ItemHolder inventory);
    boolean craft(Recipe recipe, ItemHolder inventory);
}
