package io.github.terraria.logic.building;

import io.github.terraria.logic.physics.BodyFactory;
import io.github.terraria.logic.physics.World;

import java.util.ArrayList;

public abstract class PlaneContainerBuilder {
    protected BodyFactory bodyFactory;
    protected Integer width;
    protected Integer height;
    protected Integer zeroX;
    protected Integer zeroY;
    protected World world;
    protected ArrayList<ArrayList<ArrayList<BlockType>>> savedGrid;

    public PlaneContainerBuilder bodyFactory(BodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
        return this;
    }
    public PlaneContainerBuilder width(int width) {
        this.width = width;
        return this;
    }
    public PlaneContainerBuilder height(int height) {
        this.height = height;
        return this;
    }
    public PlaneContainerBuilder zeroX(int zeroX) {
        this.zeroX = zeroX;
        return this;
    }
    public PlaneContainerBuilder zeroY(int zeroY) {
        this.zeroY = zeroY;
        return this;
    }
    public PlaneContainerBuilder world(World world) {
        this.world = world;
        return this;
    }
    public PlaneContainerBuilder savedGrid(ArrayList<ArrayList<ArrayList<BlockType>>> savedGrid) {
        this.savedGrid = savedGrid;
        return this;
    }

    public abstract PlaneContainer build();
}
