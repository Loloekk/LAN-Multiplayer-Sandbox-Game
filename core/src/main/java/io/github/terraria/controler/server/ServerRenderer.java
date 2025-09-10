package io.github.terraria.controler.server;

import io.github.terraria.logic.building.LocalPlaneContainer;
import io.github.terraria.client.view.DrawableRectangle;
import io.github.terraria.client.view.Scene;

public class ServerRenderer {

//    public Scene renderScene(){
//        Scene scene = new Scene();
//        scene.objects.add(new DrawableRectangle(1, 1, 1, 1, 1));
//        scene.objects.add(new DrawableRectangle(2, 1, 1, 1, 1));
//        scene.objects.add(new DrawableRectangle((float)(2), (float)(2), 1, 2, 0));
//        return scene;
//    }

    public Scene renderScene(LocalPlaneContainer plane){
        Scene scene = new Scene();
//        for(int x = 0; x < plane.getWidth(); x++){
//            for(int y = 0; y < plane.getHeight(); y++){
//                if(plane.getBlockAt(x, y, 0) != null){
//                    scene.objects.add(new DrawableRectangle(x, y, 1, 1, 1));
//                }
//            }
//        }
//        scene.objects.add(new DrawableRectangle(2, 2, 1, 2, 0));
        return scene;
    }
}
