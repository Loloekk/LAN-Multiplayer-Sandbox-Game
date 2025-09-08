package io.github.terraria.logic.crafting.station;

import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.crafting.Recipe;

public class FurnaceStation implements CraftingStation {
    private int coal;

    @Override
    public StationType getStationType() {
        return StationType.FURNACE;
    }

    @Override
    public boolean canCraft(Recipe recipe, ItemHolder inventory) {
        return coal >= recipe.output().getAmount() && recipe.station() == getStationType() && recipe.canCraft(inventory);
    }

    @Override
    public boolean craft(Recipe recipe, ItemHolder inventory) {
        if (!canCraft(recipe, inventory)) {
            return false;
        }
        coal -= recipe.output().getAmount();
        return recipe.craft(inventory);
    }
}
