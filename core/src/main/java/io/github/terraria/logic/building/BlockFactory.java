package io.github.terraria.logic.building;

import java.util.Map;

public class BlockFactory {
    private final Map<Integer, BlockProperties> map;
    public BlockFactory(Map<Integer, BlockProperties> map) { this.map = map; }
    public BlockType create(int id) { return new BlockTypeImpl(id, map.get(id)); }
}
