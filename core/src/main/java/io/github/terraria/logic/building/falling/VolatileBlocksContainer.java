package io.github.terraria.logic.building.falling;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VolatileBlocksContainer {
    private final List<VolatileBlock> volBlocks = new ArrayList<>();
    public void add(List<VolatileBlock> newBlocks) {
        volBlocks.addAll(newBlocks);
    };
    public List<VolatileBlock> filter() {// Zwraca wyrzucone (i.e. nieruszające się).
       Map<Boolean, List<VolatileBlock>> partitioned = volBlocks.stream()
           .collect(Collectors.partitioningBy(VolatileBlock::update));

       volBlocks.clear();
       volBlocks.addAll(partitioned.get(true));

       return partitioned.get(false);
    }
}
