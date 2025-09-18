package io.github.terraria.logic.building;

import java.util.List;

public class BlockFactory {
    private final List<BlockType> list;
    public BlockFactory(List<BlockType> list) { this.list = list; }
    public Block create(String name) {
        return new Block(list.stream()
            .filter(b -> b.name().equals(name))
            .findFirst().orElseThrow());
    }
    public Block create(int id) {
        return new Block(list.stream()
            .filter(b -> b.id() == id)
            .findFirst().orElseThrow());
    }
}
