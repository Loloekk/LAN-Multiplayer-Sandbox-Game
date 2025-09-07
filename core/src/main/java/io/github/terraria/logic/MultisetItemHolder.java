package io.github.terraria.logic;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MultisetItemHolder implements ItemHolder {
    private final Multiset<Item> set = HashMultiset.create();
    private final int capacity;
    public MultisetItemHolder(int cap) {
        capacity = max(cap,0);
    }
    public boolean insert(Item item) {
        if(set.size()==capacity)
            return false;
        set.add(item);
        return true;
    }
    public boolean insert(Item item, int count) {
        int toAdd = min(count, capacity - set.size());
        set.add(item, toAdd);
        return toAdd == count;
    }
    public int getCount(Item item) { return set.count(item); }
    public int remove(Item item, int count) {
        return min(set.remove(item, count), count);
    }
    public int remove(Item item) { return remove(item, 1); }
    public void clear() { set.clear(); }
    public Multiset<Item> browse() {
        return Multisets.unmodifiableMultiset(set);
    }
}
