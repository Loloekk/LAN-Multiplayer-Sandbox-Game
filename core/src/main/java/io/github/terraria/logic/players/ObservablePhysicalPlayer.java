package io.github.sandboxGame.logic.players;

import io.github.sandboxGame.controler.playerNetworkData.ItemHolderObserver;
import io.github.sandboxGame.controler.playerNetworkData.PhysicalPlayerObserver;
import io.github.sandboxGame.logic.actions.PlayerWorldInteractor;
import io.github.sandboxGame.logic.equipment.Item;
import io.github.sandboxGame.logic.equipment.ItemHolder;

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
