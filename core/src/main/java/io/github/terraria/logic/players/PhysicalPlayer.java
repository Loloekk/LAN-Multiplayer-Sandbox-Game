package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.actions.PlayerWorldInteractor;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.creatures.CreatureBody;
import io.github.terraria.logic.creatures.tools.PlayerTool;
import io.github.terraria.logic.equipment.ToolItem;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.logic.equipment.Item;
import io.github.terraria.logic.equipment.ItemHolder;
import io.github.terraria.logic.physics.Body;

public class PhysicalPlayer {
    private int id;
    private ItemHolder equipment;
    private Item heldItem; // Tylko takie pole spełnia nasze wymagania. Trzeba uważać przy używaniu.
    private Creature creature;
    private final PlayerWorldInteractor interactor;
    public PhysicalPlayer(ItemHolder equipment, PlayerWorldInteractor interactor) {
        this.equipment = equipment;
        this.interactor = interactor;
        this.interactor.bindPlayer(this);
    }
    public PlayerWorldInteractor getInteractor(){
        return interactor;
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
            if(heldItem instanceof ToolItem toolItem){
                creature.setTool(new PlayerTool(interactor, toolItem.getTool(interactor)));
            }
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
