package io.github.terraria.logic.crafting.station;

import io.github.terraria.common.StationType;
import io.github.terraria.logic.equipment.ItemHolder;
import io.github.terraria.logic.crafting.Recipe;

public class WorkBenchStation implements CraftingStation {

    @Override
    public StationType getStationType() {
        return StationType.WORKBENCH;
    }

    @Override
    public boolean canCraft(Recipe recipe, ItemHolder inventory) {
        return recipe.station() == getStationType() && recipe.canCraft(inventory);
    }

    @Override
    public boolean craft(Recipe recipe, ItemHolder inventory) {
        if (!canCraft(recipe, inventory)) {
            return false;
        }
        return recipe.craft(inventory);
    }
}
