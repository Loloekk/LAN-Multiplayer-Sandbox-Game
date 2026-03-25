package io.github.sandboxGame.logic.crafting.station;

import io.github.sandboxGame.common.StationType;
import io.github.sandboxGame.logic.building.Block;

import java.util.Map;

public class StationTypeMap {
    private final Map<String, StationType> map;
    public StationTypeMap(Map<String, StationType> map) { this.map = map; }
    public StationType get(Block block) { return map.get(block.type().name()); }
}
