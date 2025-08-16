package io.github.terraria.logic;

import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.players.ActivePlayers;

public record GameState(PlaneContainer grid, ActivePlayers activePlayers) { }
