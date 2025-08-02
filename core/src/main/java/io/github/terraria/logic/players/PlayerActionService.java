package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.GameState;
import io.github.terraria.logic.building.BlockType;

public abstract class PlayerActionService {
    protected final GameState gameState;
    public PlayerActionService(GameState gameState) {
        this.gameState = gameState;
    }
    // Koordynaty jak w PlaneContainer.
    protected static final int rangeRadius = 5;
    public abstract void hitAt(PhysicalPlayer physicalPlayer, Vector2 loc);
    // Położenie bloku na tę chwilę.
    public abstract void specialAt(PhysicalPlayer physicalPlayer, BlockType block, Vector2 loc);
}
