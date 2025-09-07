package io.github.terraria.logic;

import com.google.common.collect.Multiset;

// Ekwipunek albo skrzynki...
public interface ItemHolder {
    boolean insert(Item item);
    boolean insert(Item item, int count);
    int getCount(Item item);
    int remove(Item item, int count);
    int remove(Item item);
    void clear();
    Multiset<Item> browse();
}
