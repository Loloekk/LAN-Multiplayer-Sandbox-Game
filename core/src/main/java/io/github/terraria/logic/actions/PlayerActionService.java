package io.github.terraria.logic.actions;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.players.ActivePlayers;
import io.github.terraria.logic.players.PhysicalPlayer;

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
    public abstract void hitAt(PhysicalPlayer physicalPlayer, Vector2 loc);
    public abstract void stopHitting(PhysicalPlayer player);
    // Będzie potrzebne też specjalne dotknięcie, ale może to osobna metoda udostępniona zostanie
    // (głównie o crafting chodzi na tę chwilę).
    public abstract void placeHeldAt(PhysicalPlayer physicalPlayer, Vector2 loc);
}
