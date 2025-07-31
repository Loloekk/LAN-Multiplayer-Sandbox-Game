package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.Body;

public record PhysicalBlock (BlockType blockType, Body body) {}
