package io.github.terraria.logic.actions;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.crafting.CraftingService;
import io.github.terraria.logic.crafting.Recipe;
import io.github.terraria.common.StationType;
import io.github.terraria.logic.players.ActivePlayers;
import io.github.terraria.logic.players.PhysicalPlayer;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.utils.PlayerZone;

import java.util.Collections;
import java.util.List;

public class CraftingActionService {
    private final ActivePlayers activePlayers;
    private final PlaneContainer grid;
    private final CraftingService service;
    private static final int rangeRadius = 5;

    public CraftingActionService(GameState gameState, CraftingService craftingService) {
        this.activePlayers = gameState.activePlayers();
        this.grid = gameState.grid();
        this.service = craftingService;
    }

    public List<Recipe> getAvailableRecipes(PhysicalPlayer player, Vector2 loc) {
        if (!activePlayers.isActive(player.id())) {
            return Collections.emptyList();
        }
        if (!PlayerZone.isBlockInInteractionZone(player, loc, rangeRadius)) {
            return Collections.emptyList();
        }
        IntVector2 intLoc = IntVector2.toInt(loc);
        Block block = grid.getFrontBlockAt(intLoc);
        if (block == null) {
            return Collections.emptyList();
        }
        StationType type = service.getStationType(block);
        if (type == null) {
            return Collections.emptyList();
        }
        return service.getAvailableRecipes(type, player.equipment());
    }

    public boolean craft(PhysicalPlayer player, Recipe recipe, Vector2 loc) {
        List<Recipe> availableRecipes = getAvailableRecipes(player, loc);
        if (!availableRecipes.contains(recipe)) {
            return false;
        }
        return service.craft(recipe, player.equipment());
    }
}
