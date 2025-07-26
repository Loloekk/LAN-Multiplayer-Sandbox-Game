package io.github.terraria.logic;

import java.util.ArrayList;

public class StaticPlaneContainer implements PlaneContainer {
    // Słabe, że nie jest wysokość globalnie.
    private final ArrayList<PlacedBlock[]> grid = new ArrayList<>();

    @Override
    public void init(int zeroX, int zeroY, int width, int height) {

    }

    @Override
    public PlacedBlock getBlockPlacedAt(int x, int y) {
        return null;
    }

    @Override
    public void removeBlockPlacedAt(int x, int y) {

    }
}
