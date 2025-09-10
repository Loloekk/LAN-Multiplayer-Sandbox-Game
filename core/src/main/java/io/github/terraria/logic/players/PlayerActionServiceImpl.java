package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.GameState;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.building.Block;

import java.util.HashMap;
import java.util.Map;

// TODO: Test.
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
    public void hitAt(PhysicalPlayer player, Vector2 loc) {
        if(!loc.epsilonEquals(player.getPosition(), (float) rangeRadius))
            return;

        final int force = 35;
        IntVector2 intLoc = IntVector2.toInt(loc);
        Block block = gameState.grid().getFrontBlockAt(intLoc);
        if (block != null) {
            MiningAction action = currentMining.get(player.id());
            if(intLoc != currentMining.get(player.id()).location()) {
                action = new MiningAction(intLoc);
                currentMining.put(player.id(), action);
            }
            if(action.mine(force)) {
                Block block1 = gameState.grid().removeFrontBlockAt(intLoc);
                // If equipment is full block1 is garbage collected.
                // This is acceptable as PlaneContainer handles destruction of the body.
                player.equipment().insert(block1);
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
            && gameState.grid().placeBlockAt(IntVector2.toInt(loc), block)) {
            player.discardInstanceOfHeldItem();
        }
    }
}
