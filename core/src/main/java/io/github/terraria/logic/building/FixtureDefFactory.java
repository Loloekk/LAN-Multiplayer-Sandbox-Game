package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.FixtureDef;

import java.util.HashMap;
import java.util.Map;

class FixtureDefFactory {
    // Większość bloków to będzie pełny kwadrat.
    // Część bloków jest niefizyczna, null tutaj musi być zwracany.
    private static final Map<BlockType, FixtureDef> map = new HashMap<>();
    static void load() {}
    static boolean hasFixture(BlockType blockType) { return map.containsKey(blockType); }
    static FixtureDef get(BlockType blockType) { return map.get(blockType); }
}
