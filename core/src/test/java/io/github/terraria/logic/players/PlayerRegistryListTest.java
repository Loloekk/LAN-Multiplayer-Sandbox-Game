package io.github.sandboxGame.logic.players;

import com.badlogic.gdx.math.Vector2;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerRegistryListTest {
    final Vector2 firstSpawn = new Vector2(1f, 0f);
    final PlayerRegistry playerRegistry = new PlayerRegistryList(new ArrayList<>(), firstSpawn);

    @Test
    void registerPlayerSpawnTest() {
        PlayerRecord playerRecord = playerRegistry.registerPlayer("");
        assertEquals(firstSpawn, playerRecord.spawn());
    }
}
