package io.github.terraria.logic.building;

public class StaticPlaneContainerBuilder extends PlaneContainerBuilder {
    @Override
    public StaticPlaneContainer build() {
        if(width == null)
            width = StaticPlaneContainer.DEFAULT_WIDTH;
        if(height == null)
            height = StaticPlaneContainer.DEFAULT_HEIGHT;
        if(zeroX == null)
            zeroX = width / 2;
        if(zeroY == null)
            zeroY = height / 2;
        if(zeroX < 0 || zeroX >= width || zeroY < 0 || zeroY >= height || world == null)
            return null;
        // The above implies positivity of width and height.

        return new StaticPlaneContainer(width, height, zeroX, zeroY, world);
    }
}
