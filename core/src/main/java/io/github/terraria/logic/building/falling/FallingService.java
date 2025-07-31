package io.github.terraria.logic.building.falling;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import io.github.terraria.logic.building.PhysicalBlock;
import io.github.terraria.logic.building.PlaneContainer;

import java.util.ArrayList;
import java.util.List;

public class FallingService {
    private final PlaneContainer plane;
    private final VolatileBlocksContainer fallingBlocks;
    public FallingService(PlaneContainer plane, VolatileBlocksContainer fallingBlocks) {
        this.plane = plane;
        this.fallingBlocks = fallingBlocks;
    }
    public void filterFalling() {
        List<VolatileBlock> stable = fallingBlocks.filter();
        for (VolatileBlock block : stable) {
            Vector2 loc = block.body.getPosition();
            block.body.setType(BodyDef.BodyType.StaticBody);
            // I jeszcze grupa potencjalnie.
            plane.placeBlockAt((int) loc.x, (int) loc.y, block.block, block.body);
        }
    }
    // Co najwyżej warto uważać na to, żeby zarezerwować miejsce,
    // przez które nastąpi spadanie.
    // Po usunięciu bloku:
    public void setFalling(int x, int y) { // Usunięte koordynaty
        List<VolatileBlock> list = new ArrayList<>();
        while(true) {
            y++;
            PhysicalBlock phy = plane.getPhysicalAt(x, y);
            if(phy != null && phy.blockType().isFallable()) {
                phy.body().setType(BodyDef.BodyType.DynamicBody);
                // I grupa dla braku kolizji.
                VolatileBlock volBlock = new VolatileBlockImpl(phy);
                list.add(volBlock);
            }
            else
                break;
        }
        fallingBlocks.add(list);
    }
}
