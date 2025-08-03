package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.World;

public abstract class PlaneContainerBuilder {
    protected Integer width;
    protected Integer height;
    protected Integer zeroX;
    protected Integer zeroY;
    protected World world;

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

    public abstract PlaneContainer build();
}
