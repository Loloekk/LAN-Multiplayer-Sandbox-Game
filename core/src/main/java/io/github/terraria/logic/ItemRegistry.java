package io.github.terraria.logic;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    private final Map<Integer, Item> items = new HashMap<>();

    public void register(Item item) {
        items.put(item.type().id(), item);
    }

    public Item get(int id) {
        return items.get(id);
    }
}
