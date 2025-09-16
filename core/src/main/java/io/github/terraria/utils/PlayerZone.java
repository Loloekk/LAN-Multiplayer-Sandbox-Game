package io.github.terraria.utils;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.players.PhysicalPlayer;

public class PlayerZone {
    public static boolean isBlockInInteractionZone(PhysicalPlayer player, Vector2 blockPos, int radius) {
        return blockPos.epsilonEquals(player.getPosition(), (float) radius);
    }
}
