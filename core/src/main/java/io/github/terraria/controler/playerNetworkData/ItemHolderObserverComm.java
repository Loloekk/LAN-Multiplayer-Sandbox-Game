package io.github.terraria.controler.playerNetworkData;

import io.github.terraria.logic.equipment.Item;

public class ItemHolderObserverComm implements ItemHolderObserver{
    @Override
    public void onInsert(Item item, int count){
        System.out.println("Insert " +item + " " + count);
    }
    @Override
    public void onRemove(Item item, int count){
        System.out.println("Remove " +item + " " + count);
    }
}
