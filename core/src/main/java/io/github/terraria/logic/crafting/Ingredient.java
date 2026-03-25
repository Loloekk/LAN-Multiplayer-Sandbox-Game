package io.github.sandboxGame.logic.crafting;

import io.github.sandboxGame.logic.equipment.Item;

public record Ingredient(Item item, int amount) {
    public Item getItem() { return item; }
    public int getAmount() { return amount; }
}
