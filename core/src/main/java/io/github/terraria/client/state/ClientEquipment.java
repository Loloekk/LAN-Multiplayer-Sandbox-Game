package io.github.terraria.client.state;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import io.github.terraria.controler.network.PacketServerToClient.PacketCollectItems;
import io.github.terraria.controler.network.PacketServerToClient.PacketRemoveItems;
import io.github.terraria.logic.equipment.Item;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class ClientEquipment {
    private final Multiset<Integer> set = HashMultiset.create();
    public void insert(Integer item, int count) {
        set.add(item, count);
    }
    public int getCount(Integer item) { return set.count(item); }
    public void remove(Integer item, int count) {
        set.remove(item, count);
    }
    public Multiset<Integer> browse() {
        return Multisets.unmodifiableMultiset(set);
    }
    public void actualize(Object obj)
    {
        if(obj instanceof PacketCollectItems col)
        {
            insert(col.id,col.count);
        }
        else if(obj instanceof PacketRemoveItems rem)
        {
            remove(rem.id, rem.count);
        }
    }
}
