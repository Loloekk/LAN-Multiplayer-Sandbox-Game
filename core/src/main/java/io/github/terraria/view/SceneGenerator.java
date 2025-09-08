package io.github.terraria.view;

import io.github.terraria.controler.Network.Network;
import io.github.terraria.view.PlayerData.ViewPlayerData;

public class SceneGenerator {
    public static int SCENE_WIDTH = 40;
    public static int SCENE_HEIGHT = 30;
    private static float centerX = 13;
    private static float centerY = 9;

    public static Scene generate(ViewPlayerData data)
    {
        Scene scene = new Scene();
        int x = (int) data.getX();
        int y = (int) data.getY();
        float diffX = centerX - data.getX();
        float diffY = centerY - data.getY();
        for(int i = - SCENE_WIDTH / 2 ; i<= SCENE_WIDTH / 2;  i++)
            for(int j = - SCENE_HEIGHT / 2 ; j <= SCENE_HEIGHT / 2;  j++)
            {
                Integer blockId = data.getBlockId(i+x,j+y,0);
                if(blockId == null || blockId.equals(0))
                    continue;
                scene.objects.add(new DrawableRectangle(i+x+diffX + 0.5f,j+y+diffY + 0.5f,1,1,1));
            }
        for(Network.PlayerState pla : data.getPlayers())
        {
            scene.objects.add(new DrawableRectangle(pla.x+diffX,pla.y+diffY,0.8f,1.8f,0));
        }
        return scene;
    }

}
