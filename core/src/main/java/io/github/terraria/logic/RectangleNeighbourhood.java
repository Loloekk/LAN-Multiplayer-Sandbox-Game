package io.github.terraria.logic;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.players.PhysicalPlayer;
import io.github.terraria.logic.players.PlayerActivator;

public record RectangleNeighbourhood(Vector2 leftBottom, Vector2 rightTop) {

    public RectangleNeighbourhood(float leftBottomX, float leftBottomY, float rightTopX, float rightTopY) {
        this(new Vector2(leftBottomX, leftBottomY), new Vector2(rightTopX, rightTopY));
    }

    public boolean possiblyIntersects(PhysicalPlayer player) {
        Vector2 point = player.getPosition();
        float radius = PlayerActivator.MAX_PLAYERS_RADIUS;
        boolean intersectsX = (point.x >= leftBottom.x - radius && point.x <= rightTop.x + radius);
        boolean intersectsY = (point.y >= leftBottom.y - radius && point.y <= rightTop.y + radius);
        return intersectsX && intersectsY;
    }
}
