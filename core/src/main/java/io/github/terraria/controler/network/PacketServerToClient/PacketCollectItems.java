package io.github.terraria.controler.network.PacketServerToClient;

import io.github.terraria.logic.equipment.ItemType;

public class PacketCollectItems {
    int id;
    int count;
    public PacketCollectItems(){}
    public PacketCollectItems(int id, int count)
    {
        this.id = id;
        this.count = count;
    }
}
