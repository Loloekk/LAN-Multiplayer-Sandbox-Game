package io.github.terraria.logic.equipment;

import io.github.terraria.logic.creatures.Tool;
import io.github.terraria.logic.creatures.tools.MiningTool;
import io.github.terraria.logic.creatures.tools.WorldInteractor;

public record MiningToolItem(Type type) implements Item, ToolItem {
    public record Type(int id, String name, int strength, float range) implements ItemType{}
    @Override
    public Tool getTool(WorldInteractor interactor){
        return new MiningTool(interactor, type.strength, type.range);
    }
}
