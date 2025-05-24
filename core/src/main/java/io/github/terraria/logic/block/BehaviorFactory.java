package io.github.terraria.logic.block;

import java.util.Map;
import java.util.function.Supplier;

/**
 * Factory for creating BlockBehavior instances by name.
 */
public class BehaviorFactory {
    private static final Map<String, Supplier<BlockBehavior>> behaviors = Map.of(
        "solid", () -> new SolidBehavior()
    );

    public static BlockBehavior create(String name) {
        Supplier<BlockBehavior> behavior = behaviors.get(name);
        if (behavior != null) {
            return behavior.get();
        } else {
            throw new IllegalArgumentException("No such behavior: " + name);
        }
    }
}
