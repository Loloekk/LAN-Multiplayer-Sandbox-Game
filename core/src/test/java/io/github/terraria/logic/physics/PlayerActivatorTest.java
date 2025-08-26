package io.github.terraria.logic.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.players.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerActivatorTest {
    final SpawnRegistry spawnRegistry = Mockito.mock(SpawnRegistry.class);
    final World world = Mockito.mock(World.class);
    final ActivePlayers activePlayers = Mockito.mock(ActivePlayers.class);
    PlayerRegistry playerRegistry;
    PlayerActivator playerActivator;

    @BeforeEach
    void setUp() {
        playerRegistry = Mockito.mock(PlayerRegistry.class,
            Mockito.withSettings().useConstructor(spawnRegistry)
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
        playerActivator = Mockito.mock(PlayerActivator.class,
            Mockito.withSettings().useConstructor(playerRegistry, world, activePlayers)
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @Test
    void loginPlayerTest() {
        final int playersId = 10;
        final Player player = Mockito.mock(Player.class);
        Mockito.when(playerRegistry.getPlayer(playersId)).thenReturn(player);
        final Vector2 spawnPosition = new Vector2(1f, 0f);
        Mockito.when(spawnRegistry.getSpawnPosition(player)).thenReturn(spawnPosition);
        final Body body = Mockito.mock(Body.class);
        Mockito.when(playerActivator.getNewPlayerBody(spawnPosition)).thenReturn(body);

        playerActivator.loginPlayer(playersId);
        Mockito.verify(activePlayers).add(new PhysicalPlayer(player, body));
    }

    @Test
    void logoutPlayerSetSpawnPositionTest() {
        final int playersId = 10;
        final Player player = Mockito.mock(Player.class);
        final Body body = Mockito.mock(Body.class);
        Mockito.when(activePlayers.remove(playersId)).thenReturn(new PhysicalPlayer(player, body));
        final Vector2 spawnPosition = new Vector2(1f, 0f);
        Mockito.when(body.getPosition()).thenReturn(spawnPosition);

        playerActivator.logoutPlayer(playersId);
        Mockito.verify(spawnRegistry).setSpawnPosition(player, spawnPosition);
    }

    @Test
    void logoutPlayerDestroyBodyTest() {
        final int playersId = 10;
        final Player player = Mockito.mock(Player.class);
        final Body body = Mockito.mock(Body.class);
        Mockito.when(activePlayers.remove(playersId)).thenReturn(new PhysicalPlayer(player, body));

        playerActivator.logoutPlayer(playersId);
        Mockito.verify(world).destroyBody(body);
    }
}
