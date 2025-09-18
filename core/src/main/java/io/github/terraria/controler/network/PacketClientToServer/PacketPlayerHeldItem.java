package io.github.terraria.controler.network.PacketClientToServer;

public class PacketPlayerHeldItem implements PacketPlayer{
    public int playerId;
    public Integer itemId;

    public int getPlayerId() {
        return playerId;
    }
}
