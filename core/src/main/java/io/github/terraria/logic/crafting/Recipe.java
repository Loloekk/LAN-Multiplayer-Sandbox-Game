package io.github.terraria.logic.crafting;

import com.google.common.collect.Multiset;
import io.github.terraria.logic.equipment.Item;
import io.github.terraria.logic.equipment.ItemHolder;
import io.github.terraria.common.StationType;

import java.util.List;

public record Recipe(int recipeId, List<Ingredient> ingredients, Ingredient output, StationType station) {

    public boolean canCraft(ItemHolder inventory) {
        Multiset<Item> items = inventory.browse();
        for (Ingredient ingredient : ingredients) {
            if (items.count(ingredient.getItem()) < ingredient.getAmount()) {
                return false;
            }
        }
        return true;
    }

    public boolean craft(ItemHolder inventory) {
        if (!canCraft(inventory)) {
            return false;
        }
        for (Ingredient ingredient : ingredients) {
            inventory.remove(ingredient.getItem(), ingredient.getAmount());
        }
        inventory.insert(output.getItem(), output.getAmount());
        return true;
    }

}
