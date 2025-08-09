package io.github.terraria.logic;

import com.badlogic.gdx.math.Vector2;

public record IntVector2(int x, int y) {
    public float distanceTo(IntVector2 other) {
        return (float) Math.sqrt((other.x - x) * (other.x - x) + (other.y - y) * (other.y - y));
    }
    public static float distance(IntVector2 a, IntVector2 b) {
        return a.distanceTo(b);
    }
    public static IntVector2 toInt(Vector2 vec) {
        return new IntVector2((int) Math.floor(vec.x), (int) Math.floor(vec.y));
    }
    public Vector2 toFloat() {
        return new Vector2((float) x, (float) y);
    }
}
