package io.github.terraria.logic;

import java.util.Map;

public class CraftingRecipe {
    private final CraftingStationType requiredStation;
    private Map<Item, Integer> input;
    private Item output;
    private int outputAmount;

    public CraftingRecipe(CraftingStationType requiredStation) {
        this.requiredStation = requiredStation;
    }

    public boolean canCraft(Item item) { return true; }

    public void craft() {} // crafts from the input and writes to output and outputAmount

    public void add(ItemHolder inventory) {} // adds to input / removes from inventory
    public void add(ItemHolder inventory, int amount) {}

    public void remove(ItemHolder inventory) {}
    public void remove(ItemHolder inventory, int amount) {}

    public void pick(ItemHolder inventory) {} // moves the output to inventory
}
