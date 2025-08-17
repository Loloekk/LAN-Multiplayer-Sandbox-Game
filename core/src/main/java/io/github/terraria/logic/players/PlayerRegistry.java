package io.github.terraria.logic.players;

import java.util.List;

public interface PlayerRegistry {
    // Returns false if the id is occupied.
    boolean registerPlayer(int id);// Imię trzymane na wyższym poziomie?
    Player getPlayer(int id);
    List<Player> getList();
}
