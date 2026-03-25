package io.github.sandboxGame.controler.playerNetworkData;

import io.github.sandboxGame.logic.equipment.Item;

public interface PhysicalPlayerObserver {
    void onSetHeldItem(int playerId, Item item);
}
