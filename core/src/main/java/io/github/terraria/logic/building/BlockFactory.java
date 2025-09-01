package io.github.terraria.logic.building;

import java.util.Map;

public class BlockFactory {
    private final Map<Integer, BlockType> map;
    public BlockFactory(Map<Integer, BlockType> map) { this.map = map; }
    public Block create(int id) { return new Block(map.get(id)); }
}
