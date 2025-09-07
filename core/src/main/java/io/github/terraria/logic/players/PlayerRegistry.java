package io.github.terraria.logic.players;

import java.util.List;

public interface PlayerRegistry {
    Player registerPlayer();
    void updateRecord(int id, Player player);
    Player getPlayer(int id);
    List<Player> getList();
}
