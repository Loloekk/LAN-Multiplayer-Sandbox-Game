package io.github.terraria.logic.building;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BlockTypeTest {
    static final int idMin = 0, idMax = 10;
    BlockType block;

    void createBody(int id) {
/*
        block = new BlockType(id);
        World world = new World(new Vector2(0, -10), true);
        IntVector2 vector = new IntVector2(0, 0);
        Body body = block.createBody(world, vector);
        assertThat(body.getWorld() == world).withFailMessage("Fail for id: " + id).isTrue();
*/
    }

    @Test
    void createBody() {
/*
        World world = new World(new Vector2(0, -10), true);
        IntVector2 intVector2 = new IntVector2(0, 0);
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(intVector2.toFloat());
        Body body = world.createBody(bodyDef);
        assertThat(body.getWorld() == world);
*/
    }

    void getLayer(int id) {
/*
        block = new BlockTypeImpl(id, null);
        if(block.isPhysical())
            assertThat(block.getLayer() == 0).withFailMessage(
                "Wrong layer for physical id: " + id).isTrue();
*/
    }

    @Test
    void getLayer() {
        for(int i=idMin; i<idMax; i++)
            getLayer(i);
    }
}
