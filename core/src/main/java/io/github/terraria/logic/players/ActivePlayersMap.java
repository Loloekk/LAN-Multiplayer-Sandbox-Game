package io.github.terraria.logic.players;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

import java.util.List;
import java.util.Map;

public class ActivePlayersMap extends ActivePlayers {
    private final Map<Integer, PhysicalPlayer> map;
    public ActivePlayersMap(PlayerRegistry registry, World world, Map<Integer, PhysicalPlayer> map) {
        super(registry, world);
        this.map = map;
    }

    @Override
    public void loginPlayer(int playersId) {
        map.putIfAbsent(playersId, new PhysicalPlayer(registry.getPlayer(playersId), super.getNewPlayerBody()));
    }

    @Override
    public void logoutPlayer(int playersId) {
        // Destroy body.
        Body body = map.remove(playersId).body();
        world.destroyBody(body);

        // Save some player info to registry?
    }

    @Override
    public PhysicalPlayer getPlayer(int id) {
        return map.get(id);
    }

    @Override
    public List<PhysicalPlayer> getList() {
        return List.copyOf(map.values());
    }
}
