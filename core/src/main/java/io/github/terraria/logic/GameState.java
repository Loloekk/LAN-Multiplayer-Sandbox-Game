package io.github.terraria.logic;

import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.players.ActivePlayers;

public record GameState(PlaneContainer grid, ActivePlayers activePlayers) {
    // public abstract void step();
    // Poniższa funkcjonalność (i inne jak np. dane o ekwipunku gracza) jest potrzebna,
    // ale niekoniecznie jako metoda tutaj.
    // public abstract LocalMapView getLocalMapView(int x, int y, int width, int height);
}
