package io.github.sandboxGame.utils;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.players.PhysicalPlayer;

public class PlayerZone {
    public static boolean isBlockInInteractionZone(PhysicalPlayer player, Vector2 blockPos, int radius) {
        return blockPos.epsilonEquals(player.getPosition(), (float) radius);
    }
}
