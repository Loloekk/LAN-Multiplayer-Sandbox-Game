package io.github.terraria.controler.network.PacketPlayer;

public class PacketPlayerHit implements PacketPlayer{
    public int playerId;
    public float x, y;
    public int getPlayerId() {
        return playerId;
    }
}
