package io.github.terraria.controler.network.PacketClientToServer;

public class PacketCraftItems implements PacketPlayer{
    public int playerId;
    public int craftingId;
    public int getPlayerId() {
        return playerId;
    }
}
