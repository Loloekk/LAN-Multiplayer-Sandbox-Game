package io.github.terraria.logic.players;

import io.github.terraria.controler.playerNetworkData.ItemHolderObserver;
import io.github.terraria.controler.playerNetworkData.PhysicalPlayerObserver;
import io.github.terraria.logic.actions.PlayerWorldInteractor;
import io.github.terraria.logic.equipment.Item;
import io.github.terraria.logic.equipment.ItemHolder;

import java.util.ArrayList;
import java.util.List;

public class ObservablePhysicalPlayer extends PhysicalPlayer{
    private final List<PhysicalPlayerObserver> observers = new ArrayList<>();
    public ObservablePhysicalPlayer(ItemHolder equipment, PlayerWorldInteractor interactor) {
        super(equipment, interactor);
    }
    public void addObserver(PhysicalPlayerObserver obs) {
        observers.add(obs);
    }

    public void removeObserver(PhysicalPlayerObserver obs) {
        observers.remove(obs);
    }
    private void notifySetHeldItem(Item item) {
        for (PhysicalPlayerObserver obs: observers) {
            obs.onSetHeldItem(super.id(), item);
        }
        System.out.println("wysylam ze wzial item");
    }
    @Override
    public boolean setHeldItem(Item item) {
        if(super.setHeldItem(item))
        {
            notifySetHeldItem(item);
            return true;
        }
        return false;
    }
}
