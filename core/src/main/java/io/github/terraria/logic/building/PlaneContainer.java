package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.Body;

// Kontener bez logiki.
public interface PlaneContainer {
    public void init(int width, int height, int zeroX, int zeroY);
    // Zwraca z wierzchniej warstwy.
    public BlockType getBlockAt(int x, int y, int layer);
    public BlockType getFrontBlockAt(int x, int y);
    public PhysicalBlock getPhysicalAt(int x, int y);
    public void placeBlockAt(int x, int y, BlockType block);
    public void placeBlockAt(int x, int y, BlockType block, Body body);
    // Zdejmuje z wierzchniej warstwy.
    public void removeFrontBlockAt(int x, int y);
}
