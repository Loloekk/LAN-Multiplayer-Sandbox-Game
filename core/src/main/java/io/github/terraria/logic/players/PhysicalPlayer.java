package io.github.terraria.logic.players;

import com.badlogic.gdx.physics.box2d.Body;
import io.github.terraria.logic.IntVector2;

// body daje w szczególności informację o pozycji.
// Logujący gracz miałby na razie ustawiony spawn.
public record PhysicalPlayer(Player player, Body body) {
    public IntVector2 getIntegerPosition() {
        return new IntVector2((int) body.getPosition().x, (int) body.getPosition().y);
    }
}
