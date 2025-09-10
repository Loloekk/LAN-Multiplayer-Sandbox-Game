package io.github.terraria.logic;

import com.google.common.collect.Multiset;

// Ekwipunek albo skrzynki...
public interface ItemHolder {
    boolean insert(Item item);
    int insert(Item item, int count); // Returns number of items not inserted.
    int getCount(Item item);
    int remove(Item item, int count);
    int remove(Item item);
    Multiset<Item> browse();
}
