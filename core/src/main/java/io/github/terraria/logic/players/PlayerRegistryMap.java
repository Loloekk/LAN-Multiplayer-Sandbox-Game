package io.github.terraria.logic.players;

import java.util.List;
import java.util.Map;

public class PlayerRegistryMap implements PlayerRegistry {
    private final Map<Integer, Player> map;
    public PlayerRegistryMap(Map<Integer, Player> map) {
        this.map = map;
    }

    @Override
    public boolean registerPlayer(int id) {
        if(map.containsKey(id))
            return false;
        map.putIfAbsent(id, new PlayerImpl(id, PlayerRegistry.firstSpawn));
        // Initialize equipment...
        return true;
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
