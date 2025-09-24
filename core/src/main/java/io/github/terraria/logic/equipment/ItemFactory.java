package io.github.terraria.logic.equipment;

public interface ItemFactory {
    boolean contains(String name);
    boolean contains(int id);
    Item create(String name);
    Item create(int id);
}
