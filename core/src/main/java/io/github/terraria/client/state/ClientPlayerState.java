package io.github.terraria.client.state;

import io.github.terraria.common.PlayerState;
import io.github.terraria.controler.network.PacketServerToClient.PacketCollectItems;
import io.github.terraria.controler.network.PacketServerToClient.PacketRemoveItems;

public class ClientPlayerState {
    private float x;
    private float y;
    private int playerId;
    private ClientEquipment equipment;
    private Integer heldItem = null;
    private boolean difference;
    public ClientPlayerState()
    {
        equipment = new ClientEquipment();
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
        if(obj instanceof PlayerState pla)
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
