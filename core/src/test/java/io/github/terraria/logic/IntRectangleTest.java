package io.github.terraria.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IntRectangleTest {
    IntVector2 leftBottom;
    IntVector2 rightTop;
    IntRectangle rectangle;

    @BeforeEach
    void setUp() {
        leftBottom = new IntVector2(0, 0);
        rightTop = new IntVector2(9, 9);
        rectangle = new IntRectangle(leftBottom, rightTop);
    }

    @Test
    void containsLeftBottomTest() {
        assertTrue(rectangle.contains(leftBottom));
    }

    @Test
    void doesntContainRightTopTest() {
        assertFalse(rectangle.contains(rightTop));
    }
}
