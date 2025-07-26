package io.github.terraria.logic;

public interface PlaneContainer {
    public void init(int zeroX, int zeroY, int width, int height);
    public PlacedBlock getBlockPlacedAt(int x, int y);
    public void removeBlockPlacedAt(int x, int y);
    // public void generateNextColumn(boolean right);
}
