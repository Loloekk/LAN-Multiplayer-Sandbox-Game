package io.github.terraria.view.PlayerData;

public class ViewBlockState {
    public int x,y,z;
    public Integer blockID;
    public ViewBlockState() {
    }
    public ViewBlockState(int x, int y, int z, Integer blockID)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.blockID = blockID;
    }
}
