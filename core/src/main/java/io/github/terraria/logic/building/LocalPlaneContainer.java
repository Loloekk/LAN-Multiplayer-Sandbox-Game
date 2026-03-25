package io.github.sandboxGame.logic.building;

import io.github.sandboxGame.utils.IntVector2;

public interface LocalPlaneContainer {
    int getWidth();
    int getHeight();
    // Współrzędne z modelu globalne.
    Block getBlockAt(int x, int y, int layer);
    default Block getBlockAt(IntVector2 loc, int layer) {
        return getBlockAt(loc.x(), loc.y(), layer);
    }
}
