package io.github.terraria.logic.building;

import io.github.terraria.logic.physics.Body;

public record PhysicalBlock (BlockType blockType, Body body) {}
