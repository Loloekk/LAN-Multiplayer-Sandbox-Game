package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.logic.equipment.Item;
import io.github.terraria.logic.equipment.ItemHolder;
import io.github.terraria.logic.physics.Body;

public class PhysicalPlayer {
    private int id;
    private ItemHolder equipment;
    private Item heldItem; // Tylko takie pole spełnia nasze wymagania. Trzeba uważać przy używaniu.
    private Body body;
    public PhysicalPlayer(ItemHolder equipment) {
        this.equipment = equipment;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public void setBody(Body body)
    {
        this.body = body;
    }
    public int id() { return id; }
    public Body body() { return body; }
    public Item heldItem() { return heldItem; }
    public ItemHolder equipment() { return equipment; }
    public void collectItem(Item item) { equipment.insert(item); }
    public void setHeldItem(Item item) {
        if(equipment.getCount(item) > 0 || item == null) // Nie wiem czy okej Karol
            heldItem = item;
    }
    public void discardInstanceOfHeldItem() {
        equipment.remove(heldItem);
        if(equipment.getCount(heldItem) == 0) {
            setHeldItem(null);
        }
    }

    public IntVector2 getIntegerPosition() {
        Vector2 pos = body.getPosition();
        return new IntVector2((int) pos.x, (int) pos.y);
    }
    public Vector2 getPosition() { return body.getPosition(); }
}
