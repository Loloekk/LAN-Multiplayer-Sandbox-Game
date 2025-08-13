package io.github.terraria.logic.crafting.station;

import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.crafting.Recipe;

import java.util.List;

public class FurnaceStation implements CraftingStation {
    private int coal;

    @Override
    public StationType getStationType() {
        return StationType.FURNACE;
    }

    @Override
    public boolean canCraft(Recipe recipe, ItemHolder inventory) {
        return coal >= recipe.getOutput().getAmount() && recipe.getStation() == getStationType() && recipe.canCraft(inventory);
    }

    @Override
    public boolean craft(Recipe recipe, ItemHolder inventory) {
        if (!canCraft(recipe, inventory)) {
            return false;
        }
        coal -= recipe.getOutput().getAmount();
        return recipe.craft(inventory);
    }
}
