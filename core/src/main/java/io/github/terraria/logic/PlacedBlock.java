package io.github.terraria.logic;

import com.badlogic.gdx.physics.box2d.Body;

public record PlacedBlock(BlockType blockType, Body body) {}
