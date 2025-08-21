package io.github.terraria.logic.building;

import io.github.terraria.logic.IntRectangle;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.physics.Body;

// Kontener bez istotnej logiki. Tylko logika przypisania do warstw.
// Delegowanie stworzenia ciał fizycznych do innych klas.
public interface PlaneContainer {
    BlockType getBlockAt(int x, int y, int layer);
    default BlockType getBlockAt(IntVector2 loc, int layer) {
        return getBlockAt(loc.x(), loc.y(), layer);
    }
    BlockType getFrontBlockAt(int x, int y);
    default BlockType getFrontBlockAt(IntVector2 loc) {
        return getFrontBlockAt(loc.x(), loc.y());
    }
    // Współrzędnie Bodies konsekwencją położenia w tym kontenerze.
    PhysicalBlock getPhysicalAt(int x, int y);
    default PhysicalBlock getPhysicalAt(IntVector2 loc) {
        return getPhysicalAt(loc.x(), loc.y());
    }
    boolean placeBlockAt(int x, int y, BlockType block);
    default boolean placeBlockAt(IntVector2 loc, BlockType block) {
        return placeBlockAt(loc.x(), loc.y(), block);
    }
    // Ignores body if block not physical.
    boolean placeBlockAt(int x, int y, BlockType block, Body body);
    default boolean placeBlockAt(IntVector2 loc, BlockType block, Body body) {
        return placeBlockAt(loc.x(), loc.y(), block, body);
    }
    // Zdejmuje z wierzchniej warstwy.
    BlockType removeFrontBlockAt(int x, int y);
    default BlockType removeFrontBlockAt(IntVector2 loc) {
        return removeFrontBlockAt(loc.x(), loc.y());
    }
    LocalPlaneContainer getLocal(IntRectangle neighbourhood);
}
