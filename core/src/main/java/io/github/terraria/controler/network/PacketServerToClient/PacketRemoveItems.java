package io.github.terraria.controler.network.PacketServerToClient;

public class PacketRemoveItems {
    int id;
    int count;
    public PacketRemoveItems(){}
    public PacketRemoveItems(int id, int count)
    {
        this.id = id;
        this.count = count;
    }
}
