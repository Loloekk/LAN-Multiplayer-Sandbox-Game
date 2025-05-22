package io.github.terraria;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class DesktopLauncher {
    public static void main (String[] arg) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setWindowedMode(1920,1080);
        config.setForegroundFPS(60);
        config.setTitle("Terraria");
        new Lwjgl3Application(new Drop(), config);
    }
}
