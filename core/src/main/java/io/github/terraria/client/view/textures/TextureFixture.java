package io.github.terraria.client.view.textures;

import io.github.terraria.common.Config;

public record TextureFixture(String texture, float width, float height, float centerX, float centerY, boolean transparent) {
    private static String DEFAULT_MISSING_PATH = Config.MISSING_TEXTURE_PATH;
    public TextureFixture() { this(DEFAULT_MISSING_PATH,0f, 0f,0f,0f,true); }
}
