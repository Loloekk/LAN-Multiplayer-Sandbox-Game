package io.github.terraria.logic;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.building.LocalPlaneContainer;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.players.ActivePlayers;

public record GameState(PlaneContainer grid, ActivePlayers activePlayers) {
    public LocalPlaneContainer getLocalPlaneRelativeToPlayer(int playersId, Vector2 offsetToCorner) {
        var player = activePlayers.get(playersId);
        if (player == null) return null;
        var body = player.body();
        var center = body.getPosition();
        var rectangle = new RectangleNeighbourhood(
            center.sub(offsetToCorner),
            center.add(offsetToCorner)
        );
        return grid.getLocal(rectangle);
    }
}
