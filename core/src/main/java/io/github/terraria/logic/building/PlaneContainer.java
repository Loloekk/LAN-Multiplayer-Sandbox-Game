package io.github.sandboxGame.logic.building;

import io.github.sandboxGame.utils.RectangleNeighbourhood;
import io.github.sandboxGame.utils.IntVector2;
import io.github.sandboxGame.logic.physics.Body;
import io.github.sandboxGame.logic.physics.BodyFactory;
import io.github.sandboxGame.logic.physics.World;

// Kontener bez istotnej logiki. Tylko logika przypisania do warstw.
// Delegowanie stworzenia ciał fizycznych do innych klas.
public abstract class PlaneContainer {
    protected final World world;
    protected final BodyFactory bodyFactory;

    protected PlaneContainer(World world, BodyFactory bodyFactory) {
        this.world = world;
        this.bodyFactory = bodyFactory;
    }

    public abstract Block getBlockAt(int x, int y, int layer);
    public abstract Block getFrontBlockAt(int x, int y);
    public Block getFrontBlockAt(IntVector2 loc) {
        return getFrontBlockAt(loc.x(), loc.y());
    }
    // Współrzędnie Bodies konsekwencją położenia w tym kontenerze.
    public abstract PhysicalBlock getPhysicalAt(int x, int y);
    public abstract boolean placeBlockAt(int x, int y, Block block);
    public boolean placeBlockAt(IntVector2 loc, Block block) {
        return placeBlockAt(loc.x(), loc.y(), block);
    }
    // Ignores body if block not physical.
    public abstract boolean placeBlockAt(int x, int y, Block block, Body body);
    // Zdejmuje z wierzchniej warstwy.
    public abstract Block removeFrontBlockAt(int x, int y);
    public Block removeFrontBlockAt(IntVector2 loc) {
        return removeFrontBlockAt(loc.x(), loc.y());
    }
    public abstract LocalPlaneContainer getLocal(RectangleNeighbourhood neighbourhood);
    public abstract int getTopY();
}
