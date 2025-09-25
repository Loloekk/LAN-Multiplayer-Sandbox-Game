package io.github.terraria.logic.actions;

import com.badlogic.gdx.math.Vector2;
import io.github.terraria.logic.players.ActivePlayers;
import io.github.terraria.logic.players.PhysicalPlayer;
import io.github.terraria.utils.IntVector2;
import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.BlockBuilder;
import io.github.terraria.logic.building.PlaneContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PlayerActionServiceImplTest {
    IntVector2 loc;
    PhysicalPlayer player = Mockito.mock(PhysicalPlayer.class);
    PlaneContainer container = Mockito.mock(PlaneContainer.class,
            Mockito.withSettings()
                .defaultAnswer(Mockito.CALLS_REAL_METHODS));
    PlayerActionServiceImpl service;
    Block block = new BlockBuilder().build();

    @BeforeEach
    void setUp() {
        loc = new IntVector2(1, 1);
        Mockito.when(player.getPosition()).thenReturn(new Vector2(0, 0));
        Mockito.when(player.id()).thenReturn(0);

        Mockito.when(container.getFrontBlockAt(loc.x(), loc.y()))
            .thenReturn(block);
        Mockito.when(container.removeFrontBlockAt(loc.x(), loc.y()))
            .thenReturn(block);
        service = new PlayerActionServiceImpl(new GameState(container, Mockito.mock(ActivePlayers.class), null, null));
    }
    @Test
    void hitAt() {
        for(int i=0; i<3; i++)
            service.hitAt(player, loc.toFloat(), 35);
        Mockito.verify(player).collectItem(block);
        Mockito.verify(container)
            .removeFrontBlockAt(loc.x(), loc.y());
    }
}
