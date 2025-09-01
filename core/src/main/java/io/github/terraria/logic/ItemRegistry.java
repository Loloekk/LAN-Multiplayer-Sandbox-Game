package io.github.terraria.logic;

import java.util.HashMap;
import java.util.Map;

public class ItemRegistry {
    private final Map<String, Item> items = new HashMap<>();

    public void register(Item item) {
        items.put(item.type().name(), item);
    }

    public Item get(String name) {
        return items.get(name);
    }
}
