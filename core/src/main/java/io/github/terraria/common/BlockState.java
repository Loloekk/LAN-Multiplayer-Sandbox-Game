package io.github.sandboxGame.common;


public class BlockState {
    public int x,y,z;
    public Integer blockID;
    public BlockState() {
    }
    public BlockState(int x, int y, int z, Integer blockID)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockID = blockID;
    }
}
