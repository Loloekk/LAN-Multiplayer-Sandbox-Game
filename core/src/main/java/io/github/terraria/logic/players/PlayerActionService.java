package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.GameState;

public abstract class PlayerActionService {
    protected final GameState gameState;
    public PlayerActionService(GameState gameState) {
        this.gameState = gameState;
    }
    // Koordynaty jak w PlaneContainer.
    protected static final int rangeRadius = 5;
    // Można rozważyć przerobienie sygnatur na boole.
    public abstract void hitAt(PhysicalPlayer physicalPlayer, Vector2 loc);
    public abstract void stopHitting(PhysicalPlayer player);
    // Będzie potrzebne też specjalne dotknięcie, ale może to osobna metoda udostępniona zostanie
    // (głównie o crafting chodzi na tę chwilę).
    public abstract void placeHeldAt(PhysicalPlayer physicalPlayer, Vector2 loc);
}
