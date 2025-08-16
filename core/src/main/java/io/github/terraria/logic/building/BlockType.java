package io.github.terraria.logic.building;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import io.github.terraria.logic.IntVector2;
import io.github.terraria.logic.block.BlockBehavior;

public class BlockType {
    private final int id;
    // Dostępne tylko dla craftingu i renderowania.
    BlockType(int id) {
        this.id = id;
    }
    public boolean isPhysical() { return FixtureDefFactory.hasFixture(this); }
    // Lista, jeśli chcielibyśmy bardziej skomplikowane kształty z adekwatną fizyką?
    // Raczej bym się nie przejmował czymś takim.
    public FixtureDef getFixtureDef() { return FixtureDefFactory.get(this); }
    public Body createBody(World world, IntVector2 intVector2) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(intVector2.toFloat());

        Body body = world.createBody(bodyDef);
        body.createFixture(getFixtureDef());
        return body;
    }
    // isPhysical() true implies layer 0.
    public int getLayer() { return LayerFactory.get(this); }
    // To na razie można odpuścić sobie.
    public boolean canFall() { return false; }

    // Na specjalnie kliknięcie:
    public BlockBehavior getBlockBehavior() { return null; }
    public String getName() { return ""; }
}
