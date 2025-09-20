package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.CreatureBody;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.logic.equipment.Item;
import io.github.terraria.logic.equipment.ItemHolder;
import io.github.terraria.logic.physics.Body;

public class PhysicalPlayer {
    private int id;
    private ItemHolder equipment;
    private Item heldItem; // Tylko takie pole spełnia nasze wymagania. Trzeba uważać przy używaniu.
    private Creature creature;
    public PhysicalPlayer(PlayerRecord playerRecord, Creature creature) {
        this.id = playerRecord.id();
        this.equipment = playerRecord.equipment();
        this.creature = creature;
    }
    public PhysicalPlayer(ItemHolder equipment) {
        this.equipment = equipment;
    }
    public void setId(int id)
    {
        this.id = id;
    }
    public void setCreature(Creature creature)
    {
        this.creature = creature;
    }
    public int id() { return id; }
    public Creature creature() { return creature; }
    public Item heldItem() { return heldItem; }
    public ItemHolder equipment() { return equipment; }
    public void collectItem(Item item) { equipment.insert(item); }
    public boolean setHeldItem(Item item) {
        if(equipment.getCount(item) > 0 || item == null) {// Nie wiem czy okej Karol
            heldItem = item;
            return true;
        }
        return false;
    }
    public void discardInstanceOfHeldItem() {
        equipment.remove(heldItem);
        if(equipment.getCount(heldItem) == 0) {
            setHeldItem(null);
        }
    }

    public IntVector2 getIntegerPosition() {
        Vector2 pos = creature.getPosition();
        return new IntVector2((int) pos.x, (int) pos.y);
    }
    public Vector2 getPosition() { return creature.getPosition(); }
}
