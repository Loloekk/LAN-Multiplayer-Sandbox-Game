package io.github.terraria.controler.network.PacketClientToServer;

import io.github.terraria.utils.IntVector2;

public class PacketPlayerMove implements PacketPlayer{
    public int playerId;
    public IntVector2 direction;
    public Boolean jump;
    public int getPlayerId() {
        return playerId;
    }
}
