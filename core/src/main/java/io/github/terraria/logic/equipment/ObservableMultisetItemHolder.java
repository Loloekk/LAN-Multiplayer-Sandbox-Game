package io.github.terraria.logic.equipment;

import io.github.terraria.controler.playerNetworkData.ItemHolderObserver;

import com.google.common.collect.Multiset;

import java.util.ArrayList;
import java.util.List;


public class ObservableMultisetItemHolder extends MultisetItemHolder{
    private final List<ItemHolderObserver> observers = new ArrayList<>();

    public ObservableMultisetItemHolder(int cap) {
        super(cap);
    }

    public void addObserver(ItemHolderObserver obs) {
        observers.add(obs);
    }

    public void removeObserver(ItemHolderObserver obs) {
        observers.remove(obs);
    }

    private void notifyInsert(Item item, int count) {
        for (ItemHolderObserver obs : observers) {
            obs.onInsert(item, count);
        }
    }

    private void notifyRemove(Item item, int count) {
        for (ItemHolderObserver obs : observers) {
            obs.onRemove(item, count);
        }
        System.out.println(observers.size());
    }

    @Override
    public boolean insert(Item item) {
        boolean ok = super.insert(item);
        if (ok) {
            notifyInsert(item, 1);
        }
        return ok;
    }

    @Override
    public int insert(Item item, int count) {
        int added = super.insert(item, count);
        notifyInsert(item, added);
        return added;
    }

    @Override
    public int remove(Item item, int count) {
        int removed = super.remove(item, count);
        if (removed > 0) {
            notifyRemove(item, removed);
        }
        return removed;
    }

    @Override
    public int remove(Item item) {
        int removed = super.remove(item,1);
        if (removed > 0) {
            notifyRemove(item, removed);
        }
        return removed;
    }

    @Override
    public Multiset<Item> browse() {
        return super.browse();
    }
}
