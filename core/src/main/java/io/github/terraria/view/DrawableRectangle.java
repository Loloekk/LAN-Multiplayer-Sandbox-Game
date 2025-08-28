package io.github.terraria.view;

public class DrawableRectangle {
    public float centerX;
    public float centerY;
    public float width;
    public float height;
    public int textureId;
    public DrawableRectangle(float centerX, float centerY, float width, float height, int textureId){
        this.centerX = centerX;
        this.centerY = centerY;
        this.width = width;
        this.height = height;
        this.textureId = textureId;
    }

    public DrawableRectangle(){}
}
