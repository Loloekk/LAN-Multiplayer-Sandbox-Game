package io.github.terraria.logic.equipment;

import io.github.terraria.logic.creatures.Tool;
import io.github.terraria.logic.creatures.tools.WorldInteractor;

public interface ToolItem {
    Tool getTool(WorldInteractor interactor);
}
