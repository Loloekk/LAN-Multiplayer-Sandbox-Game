package io.github.terraria.logic;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.terraria.logic.building.PlaneContainer;
import io.github.terraria.logic.building.StaticPlaneContainer;

public class GameStateImpl extends GameState {
    public GameStateImpl(PlaneContainer container, World world) {
        super(container, world);
    }
    public GameStateImpl() {
        this(new StaticPlaneContainer(), new World(new Vector2(0, -10), true));
    }

    @Override
    public void step() {}
}
