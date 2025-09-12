package io.github.terraria.utils;

import com.badlogic.gdx.math.Vector2;

public record IntVector2(int x, int y) {
    public static IntVector2 toInt(Vector2 vec) {
        return new IntVector2(MathUtils.floor(vec.x), MathUtils.floor(vec.y));
    }
    public Vector2 toFloat() {
        return new Vector2((float) x, (float) y);
    }
}
