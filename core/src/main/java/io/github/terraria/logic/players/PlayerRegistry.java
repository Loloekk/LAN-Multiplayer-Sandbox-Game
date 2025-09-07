package io.github.terraria.logic.players;

import java.util.List;

public interface PlayerRegistry {
    PlayerRecord registerPlayer();
    void updateRecord(int id, PlayerRecord playerRecord);
    PlayerRecord getPlayer(int id);
    List<PlayerRecord> getList();
}
