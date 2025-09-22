package io.github.terraria.logic.actions;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.players.PhysicalPlayer;

import java.util.HashMap;
import java.util.Map;

// TODO: Test.
// But refactor first.
public class PlayerActionServiceImpl extends PlayerActionService {
    public PlayerActionServiceImpl(GameState gameState) {
        super(gameState);
    }

    private static class MiningAction {
        private final IntVector2 location;
        private int remaining = 100;
        public MiningAction(IntVector2 location) {
            this.location = location;
        }
        public boolean mine(int force) {
            // Some more complex conversion might take place here.
            remaining -= force;
            return remaining <= 0;
        }
        public IntVector2 location() { return location; }
    }
    private final Map<Integer, MiningAction> currentMining = new HashMap<>();

    @Override
    public void hitAt(PhysicalPlayer player, Vector2 loc, int force) {
        IntVector2 intLoc = IntVector2.toInt(loc);
        Block block = grid.getFrontBlockAt(intLoc);
        if (block != null) {
            MiningAction action = currentMining.get(player.id());
            if(action == null  || !intLoc.equals(action.location())) {
                action = new MiningAction(intLoc);
                currentMining.put(player.id(), action);
            }
            if(action.mine(force)) {
                Block block1 = grid.removeFrontBlockAt(intLoc);
                // If equipment is full block1 is garbage collected.
                // This is acceptable as PlaneContainer handles destruction of the body.
                player.collectItem(block1);
                if(player.heldItem() == null)
                    player.setHeldItem(block1);
                currentMining.remove(player.id());
            }
        }
        else currentMining.remove(player.id());
    }

    @Override
    public void stopHitting(PhysicalPlayer player) { currentMining.remove(player.id()); }

    @Override
    public void placeHeldAt(PhysicalPlayer player, Vector2 loc) {
        if(!loc.epsilonEquals(player.getPosition(), (float) rangeRadius))
            return;

        if(player.heldItem() instanceof Block block
            && grid.placeBlockAt(IntVector2.toInt(loc), block)) {
            player.discardInstanceOfHeldItem();
        }
    }
}
