package io.github.sandboxGame.controler.network.PacketServerToClient;

import io.github.sandboxGame.logic.equipment.ItemType;

public class PacketCollectItems {
    public int id;
    public int count;
    public PacketCollectItems(){}
    public PacketCollectItems(int id, int count)
    {
        this.id = id;
        this.count = count;
    }
}
