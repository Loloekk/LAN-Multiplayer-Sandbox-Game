package io.github.sandboxGame.controler.playerNetworkData;

import io.github.sandboxGame.logic.equipment.Item;

public interface ItemHolderObserver {
    void onInsert(Item item, int count);
    void onRemove(Item item, int count);
}
