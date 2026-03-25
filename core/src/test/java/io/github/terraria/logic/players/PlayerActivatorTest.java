package io.github.sandboxGame.logic.players;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.logic.actions.PlayerWorldInteractor;
import io.github.sandboxGame.logic.creatures.Creature;
import io.github.sandboxGame.logic.creatures.CreatureRegistry;
import io.github.sandboxGame.logic.physics.World;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerActivatorTest {
    final World world = Mockito.mock(World.class);
    final ActivePlayers activePlayers = Mockito.mock(ActivePlayers.class);
    PlayerRegistry playerRegistry = Mockito.mock(PlayerRegistry.class);
    CreatureRegistry creatureRegistry = Mockito.mock(CreatureRegistry.class);
    PlayerActivator playerActivator;

    PhysicalPlayer makePlayer(PlayerRecord record, Creature body){
        PhysicalPlayer res = new PhysicalPlayer(null, Mockito.mock(PlayerWorldInteractor.class));
        res.setId(record.id());
        res.setCreature(body);
        return res;
    }

    @BeforeEach
    void setUp() {
        playerActivator = Mockito.mock(PlayerActivator.class,
            Mockito.withSettings().useConstructor(playerRegistry, world, activePlayers, creatureRegistry)
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    }

    @Test
    void logoutPlayerSetSpawnPositionTest() {
        final int playersId = 10;
        final PlayerRecord playerRecord = new PlayerRecord(playersId, "", new Vector2(), new Vector2());
        final Creature body = Mockito.mock(Creature.class);
        final PhysicalPlayer player = makePlayer(playerRecord, body);
        Mockito.when(activePlayers.remove(playersId)).thenReturn(player);
        final Vector2 spawnPosition = new Vector2(1f, 0f);
        Mockito.when(body.getPosition()).thenReturn(spawnPosition);

        playerActivator.logoutPlayer(playerRecord);
        Mockito.verify(playerRegistry).updateRecord(Mockito.eq(playersId),
            Mockito.argThat(p -> p.id() == playersId && p.lastPos().equals(spawnPosition)));
    }

    @Test
    void logoutPlayerDestroyBodyTest() {
        final int playersId = 10;
        final PlayerRecord playerRecord = new PlayerRecord(playersId, "", new Vector2(), new Vector2());
        final Creature body = Mockito.mock(Creature.class);
        final PhysicalPlayer player = makePlayer(playerRecord, body);
        Mockito.when(activePlayers.remove(playersId)).thenReturn(player);

        playerActivator.logoutPlayer(playerRecord);
        Mockito.verify(body).destroy();
    }
}
