package io.github.terraria.controler.playerNetworkData;

import io.github.terraria.common.BlockState;
import io.github.terraria.common.Config;
import io.github.terraria.logic.building.Block;
import io.github.terraria.logic.building.PlaneContainer;

import java.util.ArrayList;

public class Chunk {
    public static int DEFAULT_WIDTH = Config.CHUNK_DEFAULT_WIDTH;
    public static int DEFAULT_HEIGHT = Config.CHUNK_DEFAULT_HEIGHT;
    public static int DEFAULT_LAYERS = Config.CHUNK_DEFAULT_LAYERS;
    public int zeroX,zeroY;

    private Integer[][][] blocks = new Integer[DEFAULT_WIDTH][DEFAULT_HEIGHT][DEFAULT_LAYERS];

    public Chunk(int zeroX, int zeroY)
    {
        this.zeroX = zeroX;
        this.zeroY = zeroY;
    }
    public ArrayList<BlockState> initialize(PlaneContainer grid)
    {
        ArrayList<BlockState> data = new ArrayList<>();
        for(int i = 0; i < DEFAULT_WIDTH; i ++)
            for(int j = 0; j < DEFAULT_HEIGHT; j ++)
                for(int z = 0; z < DEFAULT_LAYERS; z ++)
                {
                    Block tmp = grid.getBlockAt(zeroX + i, zeroY + j,z);
                    if(tmp != null)
                    {
                        blocks[i][j][z] = tmp.type().id();
                        data.add(new BlockState(zeroX+i, zeroY+j,z,blocks[i][j][z]));
                    }
                }
        return data;
    }
    public ArrayList<BlockState> getDifferences(PlaneContainer grid)
    {
        ArrayList<BlockState> data = new ArrayList<>();
        for(int i = 0; i < DEFAULT_WIDTH; i ++)
            for(int j = 0; j < DEFAULT_HEIGHT; j ++)
                for(int z = 0; z < DEFAULT_LAYERS; z ++)
                {
                    Block tmp = grid.getBlockAt(zeroX + i, zeroY + j,z);
                    if(tmp == null)
                    {
                        if(blocks[i][j][z] != null)
                        {
                            blocks[i][j][z] = null;
                            data.add(new BlockState(zeroX + i, zeroY + j, z,null));
                        }
                    }
                    else
                    {
                        if(!blocks[i][j][z].equals((Integer)tmp.type().id()))
                        {
                            blocks[i][j][z] = tmp.type().id();
                            data.add(new BlockState(zeroX + i, zeroY + j, z, blocks[i][j][z]));
                        }
                    }
                }
        return data;
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
