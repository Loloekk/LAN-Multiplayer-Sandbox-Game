package io.github.terraria.logic.building;

import io.github.terraria.logic.IntVector2;

public interface LocalPlaneContainer {
    int getWidth();
    int getHeight();
    // Uwaga, współrzędne też lokalne.
    BlockType getBlockAt(int x, int y, int layer);
    default BlockType getBlockAt(IntVector2 loc, int layer) {
        return getBlockAt(loc.x(), loc.y(), layer);
    }
}
