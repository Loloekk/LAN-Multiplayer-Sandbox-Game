package io.github.terraria.logic.crafting.station;

import io.github.terraria.logic.RecordLoader;

import java.util.Map;
import java.util.stream.Collectors;

public class StationTypeFactoryLoader {
    private final StationTypeMap stationTypeMap;
    public StationTypeFactoryLoader(String jsonName) {
        record BlockStation(String name, StationType station) {}
        var list = RecordLoader.loadList(jsonName, BlockStation.class);
        Map<String, StationType> map = list.stream()
            .filter(bs -> bs.station() != null)
            .collect(Collectors.toMap(BlockStation::name, BlockStation::station));
        stationTypeMap = new StationTypeMap(map);
    }
    public StationTypeFactoryLoader() {
        this("blocks.json");
    }
    public StationTypeMap getFactory() {
        return stationTypeMap;
    }
}
