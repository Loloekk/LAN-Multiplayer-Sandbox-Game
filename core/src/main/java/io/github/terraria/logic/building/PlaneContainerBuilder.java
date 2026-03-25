package io.github.sandboxGame.logic.building;

import io.github.sandboxGame.logic.physics.BodyFactory;
import io.github.sandboxGame.logic.physics.World;

public abstract class PlaneContainerBuilder {
    protected BodyFactory bodyFactory;
    protected World world;
    public PlaneContainerBuilder bodyFactory(BodyFactory bodyFactory) {
        this.bodyFactory = bodyFactory;
        return this;
    }
    public PlaneContainerBuilder world(World world) {
        this.world = world;
        return this;
    }

    public abstract PlaneContainer build();
}
