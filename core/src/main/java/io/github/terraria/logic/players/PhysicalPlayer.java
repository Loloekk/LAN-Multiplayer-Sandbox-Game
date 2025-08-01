package io.github.terraria.logic.players;

import com.badlogic.gdx.physics.box2d.Body;

// body daje w szczególności informację o pozycji.
// Logujący gracz miałby na razie ustawiony spawn.
public record PhysicalPlayer(Player player, Body body) {
}
