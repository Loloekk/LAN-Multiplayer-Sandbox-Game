package io.github.terraria.view.PlayerData;

public class ViewChunk {
    public static int DEFAULT_WIDTH = 5;
    public static int DEFAULT_HEIGHT = 5;
    public static int DEFAULT_LAYERS = 2;
    public int zeroX,zeroY;

    private Integer[][][] blocks = new Integer[DEFAULT_WIDTH][DEFAULT_HEIGHT][DEFAULT_LAYERS];

    public ViewChunk(int zeroX, int zeroY)
    {
        this.zeroX = zeroX;
        this.zeroY = zeroY;
    }
    public void setBlock(ViewBlockState block)
    {
        blocks[block.x-zeroX][block.y-zeroY][block.z] = block.blockID;
    }
    public Integer getBlockId(int x, int y, int z)
    {
        return blocks[x-zeroX][y-zeroY][z];
    }
    public Integer getId()
    {
        return getId(zeroX, zeroY);
    }
    public static Integer getId(Integer x, Integer y)
    {
        Integer X = getX(x);
        Integer Y = getY(y);
        return 100000*X + Y;
    }
    public static Integer getId(float x, float y)
    {
        Integer X = (int) x;
        Integer Y = (int) y;
        return getId(X,Y);
    }
    public static Integer getX(float x)
    {
        Integer X = (int) x;
        Integer R = ((X % DEFAULT_WIDTH)+DEFAULT_WIDTH)%DEFAULT_WIDTH;
        return X-R;
    }
    public static Integer getY(float y)
    {
        Integer Y = (int) y;
        Integer R = ((Y % DEFAULT_HEIGHT)+DEFAULT_HEIGHT)%DEFAULT_HEIGHT;
        return Y-R;
    }

}
