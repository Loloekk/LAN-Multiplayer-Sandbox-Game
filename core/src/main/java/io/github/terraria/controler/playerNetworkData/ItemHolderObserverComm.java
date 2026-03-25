package io.github.sandboxGame.controler.playerNetworkData;

import io.github.sandboxGame.controler.network.PacketServerToClient.PacketCollectItems;
import io.github.sandboxGame.controler.network.PacketServerToClient.PacketRemoveItems;
import io.github.sandboxGame.logic.equipment.Item;
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
        bufferTCP.add(new PacketRemoveItems(item.type().id(), count));
    }
}
