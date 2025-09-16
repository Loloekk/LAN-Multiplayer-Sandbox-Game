package io.github.terraria.client.view;

import io.github.terraria.client.state.data.ClientGameState;
import io.github.terraria.client.view.textures.TextureBank;
import io.github.terraria.client.view.textures.TextureBankLoader;
import io.github.terraria.client.view.textures.TextureQuad;
import io.github.terraria.common.Config;
import io.github.terraria.common.PlayerState;

import java.util.Stack;

public class SceneGenerator {
    public static int SCENE_WIDTH = Config.SCENE_WIDTH;
    public static int SCENE_HEIGHT = Config.SCENE_HEIGHT;
    public static int SCENE_DELTA = Config.SCENE_DELTA;
    public static int SCENE_LAYERS = Config.SCENE_LAYERS;
    private static float centerX = SCENE_WIDTH/2;
    private static float centerY = SCENE_HEIGHT/2;
    TextureBank blocksTexture;
    TextureBank playerTexture;
    public SceneGenerator()
    {
        TextureBankLoader loader = new TextureBankLoader("missing.png");
        blocksTexture = loader.getTextureBank("textureBlocks.json");
        playerTexture = loader.getTextureBank("texturePlayer.json");
    }

    public Scene generate(ClientGameState data)
    {
        Scene scene = new Scene();
        int x = (int) data.getX();
        int y = (int) data.getY();
        float diffX = centerX - data.getX();
        float diffY = centerY - data.getY();
        for(int i = - SCENE_WIDTH / 2 - SCENE_DELTA; i<= SCENE_WIDTH / 2 + SCENE_DELTA;  i++)
            for(int j = - SCENE_HEIGHT / 2 - SCENE_DELTA; j <= SCENE_HEIGHT / 2 + SCENE_DELTA;  j++) {
                Stack<DrawableRectangle> rect = new Stack<>();
                for (int z = 0; z < SCENE_LAYERS; z++) {
                    Integer blockId = data.getBlockId(i + x, j + y, z);
                    if (blockId == null || blockId.equals(0))
                        continue;
                    TextureQuad texture = blocksTexture.getTexture(blockId);
                    rect.add(new DrawableRectangle(i + x + diffX, j + y + diffY,texture));
                    if(!texture.isTransparent())
                        break;
                }
                while(!rect.isEmpty())
                {
                    scene.objects.add(rect.pop());
                }
            }
        for(PlayerState pla : data.getPlayers())
        {
            scene.objects.add(new DrawableRectangle(pla.x+diffX,pla.y+diffY,playerTexture.getTexture(0)));
//            System.out.println("player " + pla.id + " x " + pla.x + " " + pla.y);
        }
        return scene;
    }

}
