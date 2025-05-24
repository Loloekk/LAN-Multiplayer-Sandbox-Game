package io.github.terraria.logic.block;

public interface BlockBehavior {
    // the methods should communicate with the GameMap
    // e.g onPlace should call the method placeAt(x, y, this)
    void onPlace(int x, int y);
    void onBreak(int x, int y);
}
