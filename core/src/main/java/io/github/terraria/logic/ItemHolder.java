package io.github.terraria.logic;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import static java.lang.Math.max;
import static java.lang.Math.min;

// Ekwipunek albo skrzynki...
public abstract class ItemHolder {
    private final Multiset<Item> set = HashMultiset.create();
    private final int capacity;
    public ItemHolder(int cap) {
        capacity = max(cap,0);
    }
    public boolean insert(Item item) {
        if(set.size()==capacity)
            return false;
        set.add(item);
        return true;
    }
    public int getCount(Item item) { return set.count(item); }
    public int remove(Item item, int count) {
        return min(set.remove(item, count), count);
    }
    public int remove(Item item) { return remove(item, 1); }
    public Multiset<Item> browse() {
        return Multisets.unmodifiableMultiset(set);
    }
}
