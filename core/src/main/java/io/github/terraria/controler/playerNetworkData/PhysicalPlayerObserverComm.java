package io.github.sandboxGame.controler.playerNetworkData;

import io.github.sandboxGame.controler.network.PacketServerToClient.PacketPlayerHeldItem;
import io.github.sandboxGame.logic.equipment.Item;

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
        PacketPlayerHeldItem held = new PacketPlayerHeldItem();
        held.playerId = playerId;
        if(item == null)
            held.itemId = null;
        else
            held.itemId = item.type().id();
        bufferTCP.add(held);
    }
}
