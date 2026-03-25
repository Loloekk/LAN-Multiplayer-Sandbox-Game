package io.github.sandboxGame.logic.actions;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.building.PlaneContainer;
import io.github.sandboxGame.logic.players.ActivePlayers;
import io.github.sandboxGame.logic.players.PhysicalPlayer;

public abstract class PlayerActionService {
    protected final ActivePlayers activePlayers;
    protected final PlaneContainer grid;
    public PlayerActionService(GameState gameState) {
        this.activePlayers = gameState.activePlayers();
        this.grid = gameState.grid();
    }
    // Consider wrapping the below methods into two classes so as to have single responsibility principle.
    // Koordynaty jak w PlaneContainer.
    protected static final int rangeRadius = 5;
    // Można rozważyć przerobienie sygnatur na boole.
    public abstract boolean hitAt(PhysicalPlayer physicalPlayer, Vector2 loc, int force);
    public abstract void stopHitting(PhysicalPlayer player);
    // Będzie potrzebne też specjalne dotknięcie, ale może to osobna metoda udostępniona zostanie
    // (głównie o crafting chodzi na tę chwilę).
    public abstract void placeHeldAt(PhysicalPlayer physicalPlayer, Vector2 loc);
}
