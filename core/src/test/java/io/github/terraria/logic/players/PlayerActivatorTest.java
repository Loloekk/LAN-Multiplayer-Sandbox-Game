package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerActivatorTest {
    final World world = Mockito.mock(World.class);
    final ActivePlayers activePlayers = Mockito.mock(ActivePlayers.class);
    PlayerRegistry playerRegistry = Mockito.mock(PlayerRegistry.class);
    PlayerActivator playerActivator;

    @BeforeEach
    void setUp() {
        playerActivator = Mockito.mock(PlayerActivator.class,
            Mockito.withSettings().useConstructor(playerRegistry, world, activePlayers)
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @Test
    void loginPlayerTest() {
        final int playersId = 10;
        final Vector2 spawnPosition = new Vector2(1f, 0f);
        final Player player = new Player(playersId, spawnPosition);
        Mockito.when(playerRegistry.getPlayer(playersId)).thenReturn(player);

        playerActivator.loginPlayer(playersId);
        Mockito.verify(activePlayers).add(Mockito.argThat(p -> p.id() == player.id()));
    }

    @Test
    void logoutPlayerSetSpawnPositionTest() {
        final int playersId = 10;
        final Player player = new Player(playersId, new Vector2());
        final Body body = Mockito.mock(Body.class);
        Mockito.when(activePlayers.remove(playersId)).thenReturn(new PhysicalPlayer(player, body));
        final Vector2 spawnPosition = new Vector2(1f, 0f);
        Mockito.when(body.getPosition()).thenReturn(spawnPosition);

        playerActivator.logoutPlayer(playersId);
        Mockito.verify(playerRegistry).updateRecord(Mockito.eq(playersId),
            Mockito.argThat(p -> p.id() == playersId && p.spawn().equals(spawnPosition)));
    }

    @Test
    void logoutPlayerDestroyBodyTest() {
        final int playersId = 10;
        final Player player = new Player(playersId, new Vector2());
        final Body body = Mockito.mock(Body.class);
        Mockito.when(activePlayers.remove(playersId)).thenReturn(new PhysicalPlayer(player, body));

        playerActivator.logoutPlayer(playersId);
        Mockito.verify(body).destroy();
    }
}
