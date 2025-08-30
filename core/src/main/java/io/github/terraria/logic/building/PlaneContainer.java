package io.github.terraria.logic.building;

import io.github.terraria.logic.RectangleNeighbourhood;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.physics.Body;
import io.github.terraria.logic.physics.BodyFactory;
import io.github.terraria.logic.physics.World;

// Kontener bez istotnej logiki. Tylko logika przypisania do warstw.
// Delegowanie stworzenia ciał fizycznych do innych klas.
public abstract class PlaneContainer {
    protected final World world;
    protected final BodyFactory bodyFactory;

    protected PlaneContainer(World world, BodyFactory bodyFactory) {
        this.world = world;
        this.bodyFactory = bodyFactory;
    }

    public abstract BlockType getBlockAt(int x, int y, int layer);
    public BlockType getBlockAt(IntVector2 loc, int layer) {
        return getBlockAt(loc.x(), loc.y(), layer);
    }
    public abstract BlockType getFrontBlockAt(int x, int y);
    public BlockType getFrontBlockAt(IntVector2 loc) {
        return getFrontBlockAt(loc.x(), loc.y());
    }
    // Współrzędnie Bodies konsekwencją położenia w tym kontenerze.
    public abstract PhysicalBlock getPhysicalAt(int x, int y);
    public PhysicalBlock getPhysicalAt(IntVector2 loc) {
        return getPhysicalAt(loc.x(), loc.y());
    }
    public abstract boolean placeBlockAt(int x, int y, BlockType block);
    public boolean placeBlockAt(IntVector2 loc, BlockType block) {
        return placeBlockAt(loc.x(), loc.y(), block);
    }
    // Ignores body if block not physical.
    public abstract boolean placeBlockAt(int x, int y, BlockType block, Body body);
    public boolean placeBlockAt(IntVector2 loc, BlockType block, Body body) {
        return placeBlockAt(loc.x(), loc.y(), block, body);
    }
    // Zdejmuje z wierzchniej warstwy.
    public abstract BlockType removeFrontBlockAt(int x, int y);
    public BlockType removeFrontBlockAt(IntVector2 loc) {
        return removeFrontBlockAt(loc.x(), loc.y());
    }
    public abstract LocalPlaneContainer getLocal(RectangleNeighbourhood neighbourhood);
}
