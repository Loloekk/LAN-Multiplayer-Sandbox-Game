package io.github.terraria.logic.crafting;

import io.github.terraria.logic.Item;

public record Ingredient(Item item, int amount) {
    public Item getItem() { return item; }
    public int getAmount() { return amount; }
}
