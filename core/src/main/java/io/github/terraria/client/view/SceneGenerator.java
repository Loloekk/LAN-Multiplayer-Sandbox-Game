package io.github.terraria.client.view;

import io.github.terraria.client.view.Interact.Data.ViewPlayerData;
import io.github.terraria.client.view.Textures.TextureBank;
import io.github.terraria.client.view.Textures.TextureBankLoader;
import io.github.terraria.common.PlayerState;

public class SceneGenerator {
    public static int SCENE_WIDTH = 30;
    public static int SCENE_HEIGHT = 20;
    private static float centerX = 15;
    private static float centerY = 10;
    TextureBank blocksTexture;
    TextureBank playerTexture;
    public SceneGenerator()
    {
        TextureBankLoader loader = new TextureBankLoader("missing.png");
        blocksTexture = loader.getTextureBank("textureBlocks.json");
        playerTexture = loader.getTextureBank("texturePlayer.json");
    }

    public Scene generate(ViewPlayerData data)
    {
        Scene scene = new Scene();
        int x = (int) data.getX();
        int y = (int) data.getY();
        float diffX = centerX - data.getX();
        float diffY = centerY - data.getY();
        for(int i = - SCENE_WIDTH / 2 - 3; i<= SCENE_WIDTH / 2 + 3;  i++)
            for(int j = - SCENE_HEIGHT / 2 - 3; j <= SCENE_HEIGHT / 2 + 3;  j++)
            {
                Integer blockId = data.getBlockId(i+x,j+y,0);
                if(blockId == null || blockId.equals(0))
                    continue;
                scene.objects.add(new DrawableRectangle(i+x+diffX,j+y+diffY,blocksTexture.getTexture(blockId)));
            }
        for(PlayerState pla : data.getPlayers())
        {
            scene.objects.add(new DrawableRectangle(pla.x+diffX,pla.y+diffY,playerTexture.getTexture(0)));
//            System.out.println("player " + pla.id + " x " + pla.x + " " + pla.y);
        }
        return scene;
    }

}
