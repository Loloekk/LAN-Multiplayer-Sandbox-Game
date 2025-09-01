package io.github.terraria.logic.crafting.station;

import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.block.BlockBehavior;
import io.github.terraria.logic.crafting.Recipe;

public interface CraftingStation extends BlockBehavior {
    StationType getStationType();
    boolean canCraft(Recipe recipe, ItemHolder inventory);
    boolean craft(Recipe recipe, ItemHolder inventory);
}
