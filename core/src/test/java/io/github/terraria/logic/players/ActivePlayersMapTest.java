package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.ImmutableList;
import io.github.terraria.logic.actions.PlayerWorldInteractor;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.utils.RectangleNeighbourhood;
import io.github.terraria.logic.physics.Body;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ActivePlayersMapTest {
    final ActivePlayersMap map = new ActivePlayersMap(new HashMap<>());
    final List<PhysicalPlayer> physicalPlayers = new ArrayList<>();
    final List<Vector2> positions = new ArrayList<>();
    int n = 0;
    Vector2 noPos;

    Creature createBody(Vector2 position) {
        positions.add(position);
        Creature body = Mockito.mock(Creature.class);
        Mockito.when(body.getPosition()).thenReturn(position);
        Mockito.when(body.liesOn(noPos)).thenReturn(false);
        return body;
    }

    PlayerWorldInteractor createInteractor(){
        return Mockito.mock(PlayerWorldInteractor.class);
    }

    void createPhysicalPlayer(Vector2 position) {
        PhysicalPlayer physicalPlayer = new PhysicalPlayer(null, createInteractor());
        physicalPlayer.setId(n);
        physicalPlayer.setCreature(createBody(position));
        physicalPlayers.add(physicalPlayer);
        map.add(physicalPlayer);
        n++;
    }

    @BeforeEach
    void setUp() {
        noPos = new Vector2(-1f, -1f);
        createPhysicalPlayer(new Vector2(0f, 0f));
        createPhysicalPlayer(new Vector2(0f, 0.5f));
        createPhysicalPlayer(new Vector2(0.5f, 0.5f));
        createPhysicalPlayer(new Vector2(0.5f, 1f));
        createPhysicalPlayer(new Vector2(1f, 1f));
        for(int i = 0; i < n; i++) {
            Creature body = physicalPlayers.get(i).creature();
            for(int j = 0; j < n; j++) {
                Vector2 pos = positions.get(j);
                Mockito.when(body.liesOn(pos)).thenReturn(i == j);
            }
        }
    }

    @Test
    void getAtNoPos() {
        assertNull(map.getAt(noPos));
    }

    @Test
    void getAt() {
        final int i = 0;
        Vector2 pos = positions.get(i);
        PhysicalPlayer phy = physicalPlayers.get(i);
        assertEquals(phy, map.getAt(pos));
    }

    @Test
    void getLocalLeftBottom() {
        RectangleNeighbourhood rectangle = new RectangleNeighbourhood(0, 0, 2, 2);
        assertThat(map.getLocal(rectangle).getList()).containsExactlyInAnyOrderElementsOf(physicalPlayers);
    }

    @Test
    void addIdDuplicateNoOp() {
        PhysicalPlayer player = new PhysicalPlayer(null, createInteractor());
        player.setCreature(createBody(noPos));
        player.setId(0);
        map.add(player);
        assertThat(map.getList()).containsExactlyInAnyOrderElementsOf(physicalPlayers);
    }

    @Test
    void getListIsImmutable() {
        assertThat(map.getList() instanceof ImmutableList);
    }
}
