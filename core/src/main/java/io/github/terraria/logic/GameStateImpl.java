package io.github.terraria.logic;

import java.util.ArrayList;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class GameStateImpl implements GameState {
    // TODO: dependency injection.
    private final ArrayList<Player> players = new ArrayList<>(); // Cały gracz, czy tylko awatar?
    private final PlaneContainer grid = new StaticPlaneContainer();
    private final World world = new World(new Vector2(0, -10), true);
    public GameStateImpl() { // Box2D.init();
    }
    @Override
    public void step() {}
}
