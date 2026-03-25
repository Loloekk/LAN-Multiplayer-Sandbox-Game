package io.github.sandboxGame.logic.building;

import io.github.sandboxGame.logic.equipment.Item;

public record Block(BlockType type) implements Item {}
