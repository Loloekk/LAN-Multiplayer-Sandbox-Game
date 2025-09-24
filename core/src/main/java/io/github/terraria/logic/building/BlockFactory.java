package io.github.terraria.logic.building;

import io.github.terraria.logic.equipment.ItemFactory;
import org.checkerframework.checker.optional.qual.Present;

import java.util.List;

public class BlockFactory implements ItemFactory {
    private final List<BlockType> list;
    public BlockFactory(List<BlockType> list) { this.list = list; }

    @Override
    public boolean contains(String name) {
        return list.stream().anyMatch(b -> b.name().equals(name));
    }

    @Override
    public boolean contains(int id) {
        return list.stream().anyMatch(b -> b.id() == id);
    }

    @Override
    public Block create(String name) {
        return new Block(list.stream()
            .filter(b -> b.name().equals(name))
            .findFirst().orElseThrow());
    }
    @Override
    public Block create(int id) {
        return new Block(list.stream()
            .filter(b -> b.id() == id)
            .findFirst().orElseThrow());
    }
}
