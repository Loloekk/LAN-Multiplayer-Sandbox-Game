package io.github.terraria.logic.building;

import java.util.Map;

public class BlockFactory {
    private final Map<String, BlockType> map;
    public BlockFactory(Map<String, BlockType> map) { this.map = map; }
    public Block create(String name) { return new Block(map.get(name)); }
}
