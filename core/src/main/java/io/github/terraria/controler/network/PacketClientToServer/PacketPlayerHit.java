package io.github.sandboxGame.controler.network.PacketClientToServer;

public class PacketPlayerHit implements PacketPlayer{
    public int playerId;
    public float x, y;
    public int getPlayerId() {
        return playerId;
    }
}
