package io.github.sandboxGame.logic.actions;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.building.Block;
import io.github.sandboxGame.logic.building.PlaneContainer;
import io.github.sandboxGame.logic.crafting.CraftingService;
import io.github.sandboxGame.logic.crafting.Recipe;
import io.github.sandboxGame.common.StationType;
import io.github.sandboxGame.logic.players.ActivePlayers;
import io.github.sandboxGame.logic.players.PhysicalPlayer;
import io.github.sandboxGame.utils.IntVector2;
import io.github.sandboxGame.utils.PlayerZone;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

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

    public List<Recipe> getAvailableRecipes(PhysicalPlayer player) {
        if (!activePlayers.isActive(player.id())) {
            return Collections.emptyList();
        }
        List<Recipe> avail = service.getAvailableRecipes(StationType.INVENTORY, player.equipment());
        IntVector2 intLoc = IntVector2.toInt(player.getPosition());
        for (int a = -5; a <= 5; ++a) {
            for (int b = -5; b <= 5; ++b) {
                Vector2 curLoc = new Vector2(intLoc.x() + a, intLoc.y() + b);
                avail = Stream.concat(avail.stream(), getAvailableRecipes(player, curLoc).stream()).toList();
            }
        }
        return avail;
    }

    public boolean craft(PhysicalPlayer player, Recipe recipe) {
        List<Recipe> availableRecipes = getAvailableRecipes(player);
        if (!availableRecipes.contains(recipe)) {
            return false;
        }
        return service.craft(recipe, player.equipment());
    }
}
