package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;

import java.util.List;

public interface PlayerRegistry {
    Vector2 firstSpawn = new Vector2(0f, 0f);
    // Returns false if the id is occupied.
    boolean registerPlayer(int id);// Imię trzymane na wyższym poziomie?
    Player getPlayer(int id);
    List<Player> getList();
}
