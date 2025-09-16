package io.github.terraria.controler.playerNetworkData;

import io.github.terraria.logic.equipment.Item;

public interface ItemHolderObserver {
    void onInsert(Item item, int count);
    void onRemove(Item item, int count);
}
