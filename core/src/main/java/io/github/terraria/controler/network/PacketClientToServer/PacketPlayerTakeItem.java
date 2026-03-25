package io.github.sandboxGame.controler.network.PacketClientToServer;

public class PacketPlayerTakeItem implements PacketPlayer{
    public int playerId;
    public Integer itemId;

    public int getPlayerId() {
        return playerId;
    }
}
