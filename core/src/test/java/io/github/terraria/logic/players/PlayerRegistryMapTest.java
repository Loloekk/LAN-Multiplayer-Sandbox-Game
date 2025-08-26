package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class PlayerRegistryMapTest {
    final SpawnRegistry spawnRegistry = Mockito.mock(SpawnRegistry.class);
    final Vector2 firstSpawn = new Vector2(0f, 0f);
    final PlayerRegistry playerRegistry = new PlayerRegistryMap(spawnRegistry, new HashMap<>(), firstSpawn);

    @Test
    void registerPlayerTest() {
        final int playersId = 10;
        playerRegistry.registerPlayer(playersId);
        playerRegistry.registerPlayer(playersId);
        Mockito.verify(spawnRegistry, Mockito.times(1)).setSpawnPosition(Mockito.any(), Mockito.eq(firstSpawn));
    }
}
