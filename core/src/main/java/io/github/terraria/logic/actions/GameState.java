package io.github.sandboxGame.logic.actions;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.building.LocalPlaneContainer;
import io.github.sandboxGame.logic.building.PlaneContainer;
import io.github.sandboxGame.logic.creatures.CreatureRegistry;
import io.github.sandboxGame.logic.creatures.projectiles.ProjectileRegistry;
import io.github.sandboxGame.logic.players.ActivePlayers;
import io.github.sandboxGame.utils.RectangleNeighbourhood;

public record GameState(PlaneContainer grid, ActivePlayers activePlayers, CreatureRegistry creatureRegistry, ProjectileRegistry projectileRegistry) {
    public LocalPlaneContainer getLocalPlaneRelativeToPlayer(int playersId, Vector2 offsetToCorner) {
        var player = activePlayers.get(playersId);
        if (player == null) return null;
        var center = player.getPosition();
        var rectangle = new RectangleNeighbourhood(
            center.sub(offsetToCorner),
            center.add(offsetToCorner)
        );
        return grid.getLocal(rectangle);
    }
}
