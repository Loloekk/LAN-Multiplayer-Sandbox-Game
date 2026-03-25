package io.github.sandboxGame.utils;

import com.badlogic.gdx.math.Vector2;

public class IntVector2{
    private final int x;
    private final int y;

    public IntVector2(){
        x = 0; y = 0;
    }
    public IntVector2(int x, int y){
        this.x = x;
        this.y = y;
    }
    public int x(){return x;}
    public int y(){return y;}
    public static IntVector2 toInt(Vector2 vec) {
        return new IntVector2(MathUtils.floor(vec.x), MathUtils.floor(vec.y));
    }
    public Vector2 toFloat() {
        return new Vector2((float) x, (float) y);
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof  IntVector2 other){
            return other.x() == x && other.y() == y;
        }else{
            return false;
        }
    }
}
