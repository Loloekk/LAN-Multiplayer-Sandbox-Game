package io.github.terraria.logic.players;

import com.badlogic.gdx.math.Vector2;
import com.google.common.collect.ImmutableList;
import io.github.terraria.logic.IntRectangle;
import io.github.terraria.logic.IntVector2;
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

    Body createBody(Vector2 position) {
        positions.add(position);
        Body body = Mockito.mock(Body.class);
        Mockito.when(body.getPosition()).thenReturn(position);
        Mockito.when(body.liesOn(noPos)).thenReturn(false);
        return body;
    }

    void createPhysicalPlayer(Vector2 position) {
        PhysicalPlayer physicalPlayer = new PhysicalPlayer(new PlayerImpl(n), createBody(position));
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
            Body body = physicalPlayers.get(i).body();
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
        IntRectangle rectangle = new IntRectangle(0, 0, 2, 2);
        assertThat(map.getLocal(rectangle).getList()).containsExactlyInAnyOrderElementsOf(physicalPlayers);
    }

    @Test
    void getLocalRightTop() {
        IntRectangle rectangle = new IntRectangle(0, 0, 1, 1);
        assertThat(map.getLocal(rectangle).getList()).containsExactlyInAnyOrderElementsOf(physicalPlayers.subList(0, 3));
    }

    @Test
    void getLocalDegenerate() {
        IntVector2 pos = IntVector2.toInt(positions.get(0));
        IntRectangle rectangle = new IntRectangle(pos, pos);
        assertThat(map.getLocal(rectangle).getList()).isEmpty();
    }

    @Test
    void addIdDuplicateNoOp() {
        map.add(new PhysicalPlayer(new PlayerImpl(0), Mockito.mock(Body.class)));
        assertThat(map.getList()).containsExactlyInAnyOrderElementsOf(physicalPlayers);
    }

    @Test
    void getListIsImmutable() {
        assertThat(map.getList() instanceof ImmutableList);
    }
}
