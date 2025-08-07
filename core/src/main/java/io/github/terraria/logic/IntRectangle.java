package io.github.terraria.logic;

import com.badlogic.gdx.math.Vector2;

public record IntRectangle(IntVector2 leftBottom, IntVector2 rightTop) {

    public IntRectangle(int leftBottomX, int leftBottomY, int rightTopX, int rightTopY) {
        this(new IntVector2(leftBottomX, leftBottomY), new IntVector2(rightTopX, rightTopY));
    }

    public boolean contains(IntVector2 point) {
        return point.x() >= leftBottom.x() && point.x() < rightTop.x() &&
               point.y() >= leftBottom.y() && point.y() < rightTop.y();
    }
    public boolean contains(Vector2 point) {
        return contains(IntVector2.toInt(point));
    }
}
