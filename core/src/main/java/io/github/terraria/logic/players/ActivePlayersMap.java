package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Fixture;

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

    public static boolean liesOn(Vector2 desired, Body body) {
        for (Fixture fixture : body.getFixtureList()) {
            if (fixture.testPoint(desired)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public PhysicalPlayer getAt(Vector2 desired) {
        for (PhysicalPlayer player : map.values()) {
            if (liesOn(desired, player.body()))
                return player;
        }
        return null;
    }
}
