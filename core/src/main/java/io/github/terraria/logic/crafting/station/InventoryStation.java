package io.github.terraria.logic.crafting.station;

import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.crafting.Recipe;

public class InventoryStation implements CraftingStation {

    @Override
    public StationType getStationType() {
        return StationType.INVENTORY;
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
