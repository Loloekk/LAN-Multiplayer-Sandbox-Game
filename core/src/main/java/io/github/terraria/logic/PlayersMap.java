package io.github.terraria.logic;

import java.util.List;
import java.util.Map;

public class PlayersMap implements PlayersContainer {
    private final Map<Integer, Player> map;
    public PlayersMap(Map<Integer, Player> map) {
        this.map = map;
    }

    @Override
    public void addPlayer(Player player) {
        map.put(player.getId(), player);
    }

    @Override
    public Player getPlayer(int id) {
        return map.get(id);
    }

    @Override
    public List<Player> getList() {
        // return map.values();
        return List.of(map.values().toArray());
    }
}
