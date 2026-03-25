package io.github.sandboxGame.logic.equipment;

import io.github.sandboxGame.logic.creatures.Tool;
import io.github.sandboxGame.logic.creatures.tools.WorldInteractor;

public interface ToolItem {
    Tool getTool(WorldInteractor interactor);
}
