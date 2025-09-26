package io.github.terraria.logic.crafting.station;

import io.github.terraria.common.StationType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CraftingStationRegistry {
    private final Map<StationType, CraftingStation> stations;
    public CraftingStationRegistry(List<CraftingStation> stations) {
        this.stations = stations.stream()
            .collect(Collectors.toMap(CraftingStation::getStationType, s -> s));
    }
    public CraftingStation get(StationType type) {
        return stations.get(type);
    }
    public Map<StationType, CraftingStation> all() {
        return stations;
    }
}
