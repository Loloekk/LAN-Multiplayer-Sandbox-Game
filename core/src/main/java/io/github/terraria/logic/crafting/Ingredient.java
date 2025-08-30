package io.github.terraria.logic.crafting;

import io.github.terraria.logic.Item;
import io.github.terraria.logic.ItemRegistry;

public class Ingredient {
    private Item item;
    private int amount;

    public Ingredient(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public Item getItem() { return item; }
    public int getAmount() { return amount; }
}
