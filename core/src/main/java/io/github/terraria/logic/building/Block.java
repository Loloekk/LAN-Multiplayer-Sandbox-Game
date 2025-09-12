package io.github.terraria.logic.building;

import io.github.terraria.logic.equipment.Item;

public record Block(BlockType type) implements Item {}
