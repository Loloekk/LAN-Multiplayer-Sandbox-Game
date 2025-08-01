package io.github.terraria.logic.players;

import com.badlogic.gdx.physics.box2d.Body;

public interface Player {
    public int getId();
    public Body getBody();
    public void setBody(Body body);
    // getEquipment();
}
