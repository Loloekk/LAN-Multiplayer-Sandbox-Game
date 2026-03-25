package io.github.sandboxGame.logic.crafting.station;

import io.github.sandboxGame.common.StationType;
import io.github.sandboxGame.logic.equipment.ItemHolder;
import io.github.sandboxGame.logic.crafting.Recipe;

public class FurnaceStation implements CraftingStation {
    private int coal = Integer.MAX_VALUE;

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
