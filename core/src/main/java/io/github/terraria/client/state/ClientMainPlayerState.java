package io.github.terraria.client.state;

import io.github.terraria.controler.network.PacketServerToClient.PacketPlayerState;
import io.github.terraria.controler.network.PacketServerToClient.PacketCollectItems;
import io.github.terraria.controler.network.PacketServerToClient.PacketPlayerHeldItem;
import io.github.terraria.controler.network.PacketServerToClient.PacketRemoveItems;

public class ClientMainPlayerState {
    private float x;
    private float y;
    private int playerId;
    private ClientEquipment equipment;
    private Integer heldItem = null;
    private boolean difference;
    public ClientMainPlayerState()
    {
        equipment = new ClientEquipment();
        equipment.updateAvailability();
        difference = true;
    }
    public void setPlayerId(int id)
    {
        playerId = id;
    }

    public float getX()
    {
        return x;
    }
    public float getY()
    {
        return y;
    }
    public int getPlayerId()
    {
        return playerId;
    }
    public Integer getHeldItem()
    {
        return heldItem;
    }
    public void actualize(Object obj)
    {
        if(obj instanceof PacketPlayerState pla)
        {
            if(pla.id == playerId)
            {
                x = pla.x;
                y = pla.y;
            }
        }
        if(obj instanceof PacketCollectItems || obj instanceof PacketRemoveItems)
        {
            equipment.actualize(obj);
            difference = true;
        }
        if(obj instanceof PacketPlayerHeldItem held)
        {
            if(held.playerId == playerId)
            {
                heldItem = held.itemId;
                difference = true;
            }
        }
    }
    public ClientEquipment getEquipment()
    {
        return equipment;
    }
    public boolean hasDifference()
    {
        boolean ret = difference;
        difference = false;
        return ret;
    }
}
