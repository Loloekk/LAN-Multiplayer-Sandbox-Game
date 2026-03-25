package io.github.sandboxGame.logic.players;

import java.util.List;

public interface PlayerRegistry {
    PlayerRecord registerPlayer(String name);
    void updateRecord(int id, PlayerRecord playerRecord);
    PlayerRecord getPlayer(int id);
    List<PlayerRecord> getList();
    boolean hasPlayer(int id);
    boolean hasPlayer(String name);
    int getId(String name);
}
