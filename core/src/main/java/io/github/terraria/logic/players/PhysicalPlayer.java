package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.physics.Body;

// body daje w szczególności informację o pozycji.
// Logujący gracz miałby na razie ustawiony spawn.
public record PhysicalPlayer(Player player, Body body) {
    public IntVector2 getIntegerPosition() {
        Vector2 pos = body.getPosition();
        return new IntVector2((int) pos.x, (int) pos.y);
    }public Vector2 getPosition() {
        Vector2 pos = body.getPosition();
        return new Vector2(pos.x, pos.y);
    }
}
