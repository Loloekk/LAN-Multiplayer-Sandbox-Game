package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import io.github.terraria.logic.IntVector2;

// body daje w szczególności informację o pozycji.
// Logujący gracz miałby na razie ustawiony spawn.
public record PhysicalPlayer(Player player, Body body) {
    public IntVector2 getIntegerPosition() {
        Vector2 pos = body.getPosition();
        return new IntVector2((int) pos.x, (int) pos.y);
    }
}
