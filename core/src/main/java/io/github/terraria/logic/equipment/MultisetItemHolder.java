package io.github.terraria.logic.equipment;

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
    @Override
    public boolean insert(Item item) {
        if(set.size()==capacity)
            return false;
        set.add(item);
        return true;
    }
    @Override
    public int insert(Item item, int count) {
        int toAdd = min(count, capacity - set.size());
        set.add(item, toAdd);
        return toAdd; // zmiana nie zwraca ile miejsca zostało tylko ile wrzucono elementow
    }
    @Override
    public int getCount(Item item) { return set.count(item); }
    @Override
    public int remove(Item item, int count) {
        return min(set.remove(item, count), count);
    }
    @Override
    public int remove(Item item) { return remove(item, 1); }
    @Override
    public Multiset<Item> browse() {
        return Multisets.unmodifiableMultiset(set);
    }
}
