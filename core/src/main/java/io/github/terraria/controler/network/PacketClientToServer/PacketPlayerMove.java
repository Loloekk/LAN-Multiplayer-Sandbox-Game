package io.github.terraria.controler.network.PacketClientToServer;

public class PacketPlayerMove implements PacketPlayer{
    public int playerId;
    public short moveX;
    public Boolean jump;
    public int getPlayerId() {
        return playerId;
    }
}
