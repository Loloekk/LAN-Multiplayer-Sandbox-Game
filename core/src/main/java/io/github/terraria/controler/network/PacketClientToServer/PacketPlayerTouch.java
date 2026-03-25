package io.github.sandboxGame.controler.network.PacketClientToServer;

public class PacketPlayerTouch implements PacketPlayer{
    public int playerId;
    public float x, y;
    public int getPlayerId() {
        return playerId;
    }
}
