package io.github.terraria.controler.playerNetworkData;

import io.github.terraria.logic.equipment.Item;

public interface PhysicalPlayerObserver {
    void onSetHeldItem(int playerId, Item item);
}
