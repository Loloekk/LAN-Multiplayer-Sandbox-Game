package io.github.terraria.loading;

import io.github.terraria.logic.crafting.station.StationType;
import io.github.terraria.logic.crafting.station.StationTypeMap;

import java.util.Map;
import java.util.stream.Collectors;

public class StationTypeMapLoader {
    private final StationTypeMap stationTypeMap;
    public StationTypeMapLoader(String jsonName) {
        record BlockStation(String name, StationType station) {}
        var list = RecordLoader.loadList(jsonName, BlockStation.class);
        Map<String, StationType> map = list.stream()
            .filter(bs -> bs.station() != null)
            .collect(Collectors.toMap(BlockStation::name, BlockStation::station));
        stationTypeMap = new StationTypeMap(map);
    }
    public StationTypeMapLoader() {
        this("blocks.json");
    }
    public StationTypeMap getFactory() {
        return stationTypeMap;
    }
}
