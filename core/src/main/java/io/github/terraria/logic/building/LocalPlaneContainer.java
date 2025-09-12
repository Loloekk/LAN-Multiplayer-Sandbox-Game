package io.github.terraria.logic.building;

import io.github.terraria.utils.IntVector2;

public interface LocalPlaneContainer {
    int getWidth();
    int getHeight();
    // Współrzędne z modelu globalne.
    Block getBlockAt(int x, int y, int layer);
    default Block getBlockAt(IntVector2 loc, int layer) {
        return getBlockAt(loc.x(), loc.y(), layer);
    }
}
