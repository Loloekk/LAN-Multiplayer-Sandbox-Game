package io.github.terraria.controler.playerNetworkData;

import io.github.terraria.controler.network.PacketServerToClient.PacketPlayerTakeItem;
import io.github.terraria.logic.equipment.Item;

import java.util.List;

public class PhysicalPlayerObserverComm implements PhysicalPlayerObserver{
    List<Object> bufferTCP;
    public PhysicalPlayerObserverComm(List<Object> bufferTCP)
    {
        this.bufferTCP = bufferTCP;
    }
    @Override
    public void onSetHeldItem(int playerId, Item item){
        System.out.println(playerId + " held item " + item);
        PacketPlayerTakeItem take = new PacketPlayerTakeItem();
        take.playerId = playerId;
        if(item == null)
            take.itemId = null;
        else
            take.itemId = item.type().id();
        bufferTCP.add(take);
    }
}
