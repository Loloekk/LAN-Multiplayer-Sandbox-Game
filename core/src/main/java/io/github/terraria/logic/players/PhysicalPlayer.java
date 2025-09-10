package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.Item;
import io.github.terraria.logic.ItemHolder;
import io.github.terraria.logic.physics.Body;

public class PhysicalPlayer {
    private final int id;
    private final ItemHolder equipment;
    private Item heldItem; // Tylko takie pole spełnia nasze wymagania. Trzeba uważać przy używaniu.
    private final Body body;
    public PhysicalPlayer(PlayerRecord playerRecord, Body body) {
        this.id = playerRecord.id();
        this.equipment = playerRecord.equipment();
        this.body = body;
        // this.spawn = player.spawn(); This might be important once players can die.
    }
    public int id() { return id; }
    public Body body() { return body; }
    public Item heldItem() { return heldItem; }
    public ItemHolder equipment() { return equipment; }
    public void collectItem(Item item) { equipment.insert(item); }
    public void setHeldItem(Item item) { heldItem = item; }
    public void discardInstanceOfHeldItem() {
        equipment.remove(heldItem);
        if(equipment.getCount(heldItem) == 0)
            heldItem = null;
    }

    public IntVector2 getIntegerPosition() {
        Vector2 pos = body.getPosition();
        return new IntVector2((int) pos.x, (int) pos.y);
    }
    public Vector2 getPosition() { return body.getPosition(); }
}
