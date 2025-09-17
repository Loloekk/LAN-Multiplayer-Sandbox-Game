package io.github.terraria.controler.network.PacketClientToServer;

public class PacketPlayerHeldItem implements PacketPlayer{
    public int playerId;
    public int itemId;

    public int getPlayerId() {
        return playerId;
    }
}
