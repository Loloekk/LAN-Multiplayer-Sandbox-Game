package io.github.sandboxGame.controler.network.PacketClientToServer;

import io.github.sandboxGame.utils.IntVector2;

public class PacketPlayerMove implements PacketPlayer{
    public int playerId;
    public IntVector2 direction;
    public Boolean jump;
    public int getPlayerId() {
        return playerId;
    }
}
