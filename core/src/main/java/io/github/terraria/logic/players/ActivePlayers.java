package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface ActivePlayers {
    void add(PhysicalPlayer player);
    PhysicalPlayer remove(int playersId);
    PhysicalPlayer get(int playersId);
    List<PhysicalPlayer> getList();
    PhysicalPlayer getAt(Vector2 desired);
}
