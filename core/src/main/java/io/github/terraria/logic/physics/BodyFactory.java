package io.github.terraria.logic.physics;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.building.BlockType;

import java.util.Map;

public class BodyFactory {
    // Większość bloków to będzie pełny kwadrat.
    // Część bloków jest niefizyczna, null tutaj musi być zwracany.
    private final Map<Integer, FixtureDef> map;
    public BodyFactory(Map<Integer, FixtureDef> map) { this.map = map; }
    public Body create(BlockType blockType, World world, IntVector2 intVector2) {
        return world.createStaticBody(intVector2.toFloat(), map.get(blockType.id()));
    }
}
