package io.github.terraria.controler.network.PacketClientToServer;

public class PacketPlayerTouch implements PacketPlayer{
    public int playerId;
    public float x, y;
    public int getPlayerId() {
        return playerId;
    }
}
