package io.github.terraria.logic.physics;

import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import io.github.terraria.logic.IntRectangle;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.building.BlockType;
import io.github.terraria.logic.building.BlockTypeImpl;

import java.awt.geom.RectangularShape;
import java.util.HashMap;
import java.util.Map;

public class BodyFactory {
    // Większość bloków to będzie pełny kwadrat.
    // Część bloków jest niefizyczna, null tutaj musi być zwracany.
    private static final Map<BlockType, FixtureDef> map = new HashMap<>();
    // Load implicit in any method?
    // Narzut wydajnościowy.
    public static void load() {}
    public static boolean isPhysical(BlockType blockType) { return map.containsKey(blockType); }
    public static Body createBody(BlockType blockType, World world, IntVector2 intVector2) {
        return world.createStaticBody(intVector2.toFloat(), map.get(blockType));
    }
}
