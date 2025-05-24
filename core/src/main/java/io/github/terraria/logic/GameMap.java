package io.github.terraria.logic;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import io.github.terraria.logic.block.Block;

// Myślę, że na postaci / moby potrzebna jest osobna mapa.
public class GameMap {
    private final Map<Pair<Integer, Integer>, Block> map = new HashMap<>();
    public boolean placeAt(int x, int y, Block bl) {
        return (map.putIfAbsent(Pair.of(x, y), bl) == null);
    }
    public Block getAt(int x, int y) {
        return map.get(Pair.of(x,y));
    }
    public Block removeAt(int x, int y) {
        return map.remove(Pair.of(x,y));
    }
}
