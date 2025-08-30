package io.github.terraria.logic;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.players.PhysicalPlayer;
import io.github.terraria.logic.players.Player;
import io.github.terraria.logic.players.PlayerActivator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class RectangleNeighbourhoodTest {
    Vector2 leftBottom;
    Vector2 rightTop;
    RectangleNeighbourhood rectangle;
    Body body;
    PhysicalPlayer player;

    @BeforeEach
    void setUp() {
        leftBottom = new Vector2(0f, 0f);
        rightTop = new Vector2(9f, 9f);
        rectangle = new RectangleNeighbourhood(leftBottom, rightTop);
        body = Mockito.mock(Body.class);
        player = new PhysicalPlayer(Mockito.mock(Player.class), body);
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
