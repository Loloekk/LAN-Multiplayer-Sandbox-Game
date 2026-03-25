package io.github.sandboxGame.logic.physics;

import io.github.sandboxGame.utils.IntVector2;
import io.github.sandboxGame.logic.building.Block;

import java.util.Map;

public class BodyFactory {
    // Większość bloków to będzie pełny kwadrat.
    // Część bloków jest niefizyczna, null tutaj musi być zwracany.
    private final Map<Integer, BlockFixture> map;
    public BodyFactory(Map<Integer, BlockFixture> map) { this.map = map; }
    public Body create(Block block, World world, IntVector2 intVector2) {
        return world.createBlockBody(intVector2.toFloat().add(0.5f, 0.5f), map.get(block.type().id()));
    }
}
