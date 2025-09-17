package io.github.terraria.controler.playerNetworkData;

import io.github.terraria.controler.network.PacketServerToClient.PacketCollectItems;
import io.github.terraria.logic.equipment.Item;
import java.util.List;

public class ItemHolderObserverComm implements ItemHolderObserver{
    List<Object> bufferTCP;
    public ItemHolderObserverComm(List<Object> bufferTCP)
    {
        this.bufferTCP = bufferTCP;
    }
    @Override
    public void onInsert(Item item, int count){
        System.out.println("Insert " + item + " " + count);
        bufferTCP.add(new PacketCollectItems(item.type().id(), count));
    }
    @Override
    public void onRemove(Item item, int count){
        System.out.println("Remove " + item + " " + count);
        bufferTCP.add(new PacketCollectItems(item.type().id(), count));
    }
}
