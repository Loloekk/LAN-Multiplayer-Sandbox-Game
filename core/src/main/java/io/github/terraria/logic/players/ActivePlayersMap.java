package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.IntRectangle;

import java.util.List;
import java.util.Map;

public class ActivePlayersMap implements ActivePlayers {
    private final Map<Integer, PhysicalPlayer> map;
    public ActivePlayersMap(Map<Integer, PhysicalPlayer> map) {
        this.map = map;
    }

    @Override
    public void add(PhysicalPlayer player) {
        map.putIfAbsent(player.player().getId(), player);
    }

    @Override
    public PhysicalPlayer remove(int playersId) {
        return map.remove(playersId);
    }

    @Override
    public PhysicalPlayer get(int playersId) {
        return map.get(playersId);
    }

    @Override
    public List<PhysicalPlayer> getList() {
        return List.copyOf(map.values());
    }

    @Override
    public PhysicalPlayer getAt(Vector2 desired) {
        for (PhysicalPlayer player : map.values()) {
            if (player.body().liesOn(desired))
                return player;
        }
        return null;
    }

    @Override
    public ActivePlayers getLocal(IntRectangle rectangle) {
        Map<Integer, PhysicalPlayer> filteredMap = map.entrySet().stream()
                .filter(entry -> rectangle.contains(entry.getValue().body().getPosition()))
                .collect(java.util.stream.Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        return new ActivePlayersMap(filteredMap);
    }
}
