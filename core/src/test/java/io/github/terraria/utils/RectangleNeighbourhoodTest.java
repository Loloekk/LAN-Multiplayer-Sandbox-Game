package io.github.terraria.utils;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.actions.PlayerWorldInteractor;
import io.github.terraria.logic.creatures.Creature;
import io.github.terraria.logic.players.PhysicalPlayer;
import io.github.terraria.logic.players.PlayerRecord;
import io.github.terraria.logic.players.PlayerActivator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RectangleNeighbourhoodTest {
    Vector2 leftBottom;
    Vector2 rightTop;
    RectangleNeighbourhood rectangle;
    Creature body;
    PhysicalPlayer player;
    PhysicalPlayer makePlayer(PlayerRecord record, Creature body){
        PhysicalPlayer res = new PhysicalPlayer(null, Mockito.mock(PlayerWorldInteractor.class));
        res.setId(record.id());
        res.setCreature(body);
        return res;
    }

    @BeforeEach
    void setUp() {
        leftBottom = new Vector2(0f, 0f);
        rightTop = new Vector2(9f, 9f);
        rectangle = new RectangleNeighbourhood(leftBottom, rightTop);
        body = Mockito.mock(Creature.class);
        player = makePlayer(new PlayerRecord(0, "", new Vector2(), new Vector2()), body);
    }

    @Test
    void containsLeftBottomTest() {
        Mockito.when(body.getPosition()).thenReturn(leftBottom.sub(
            new Vector2(PlayerActivator.MAX_PLAYERS_RADIUS / 2, PlayerActivator.MAX_PLAYERS_RADIUS / 3)));
        assertTrue(rectangle.possiblyIntersects(player));
    }

    @Test
    void containsRightTopTest() {
        Mockito.when(body.getPosition()).thenReturn(rightTop.add(
            new Vector2(PlayerActivator.MAX_PLAYERS_RADIUS / 3, PlayerActivator.MAX_PLAYERS_RADIUS / 2)));
        assertTrue(rectangle.possiblyIntersects(player));
    }
}
