package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;

class PlayerRegistryListTest {
    final SpawnRegistry spawnRegistry = Mockito.mock(SpawnRegistry.class);
    final Vector2 firstSpawn = new Vector2(0f, 0f);
    final PlayerRegistry playerRegistry = new PlayerRegistryList(spawnRegistry, new ArrayList<>(), firstSpawn);

    @Test
    void registerPlayerSpawnTest() {
        Player player = playerRegistry.registerPlayer();
        Mockito.verify(spawnRegistry, Mockito.times(1)).setSpawnPosition(player, firstSpawn);
    }
}
