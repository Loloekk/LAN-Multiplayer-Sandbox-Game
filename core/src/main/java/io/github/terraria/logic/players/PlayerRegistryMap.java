package io.github.terraria.logic.players;

import java.util.List;
import java.util.Map;

public class PlayerRegistryMap implements PlayerRegistry {
    private final Map<Integer, Player> map;
    public PlayerRegistryMap(Map<Integer, Player> map) {
        this.map = map;
    }

    @Override
    public void registerPlayer(int id) {
        // Initialize equipment, position...
    }

    @Override
    public Player getPlayer(int id) {
        return map.get(id);
    }

    @Override
    public List<Player> getList() {
        return List.copyOf(map.values());
    }
}
