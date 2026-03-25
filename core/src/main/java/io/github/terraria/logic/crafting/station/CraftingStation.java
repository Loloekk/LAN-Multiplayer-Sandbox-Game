package io.github.sandboxGame.logic.crafting.station;

import io.github.sandboxGame.common.StationType;
import io.github.sandboxGame.logic.equipment.ItemHolder;
import io.github.sandboxGame.logic.crafting.Recipe;

public interface CraftingStation {
    StationType getStationType();
    boolean canCraft(Recipe recipe, ItemHolder inventory);
    boolean craft(Recipe recipe, ItemHolder inventory);
}
