package io.github.sandboxGame.logic.physics;

import com.badlogic.gdx.math.Vector2;
import io.github.sandboxGame.utils.IntVector2;
import io.github.sandboxGame.logic.building.BlockBuilder;
import io.github.sandboxGame.logic.building.PhysicalBlock;
import io.github.sandboxGame.logic.building.PlaneContainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class DefaultPlayerActivatorTest {
    World world = Mockito.mock(World.class);
    PlaneContainer planeContainer = Mockito.mock(PlaneContainer.class);
    DefaultPlayerActivator activator = new DefaultPlayerActivator(null, world, null, planeContainer, null, null);
    final int x = 4;

    @BeforeEach
    void setUp() {
        Mockito.when(planeContainer.getTopY()).thenReturn(10);
        Mockito.when(planeContainer.getPhysicalAt(Mockito.eq(x), Mockito.anyInt())).thenAnswer(invocation -> {
            int y = invocation.getArgument(1);
            return y == 3 || y == 5 ?
            new PhysicalBlock(new BlockBuilder().build(), Mockito.mock(Body.class)) : null;
        });
    }

    @Test
    void shiftToFreeTest() {
        assertEquals(new Vector2(x, 7f),
            activator.shiftToFree(new IntVector2(x, 3), 2));
    }
}
