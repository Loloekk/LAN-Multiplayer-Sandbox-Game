package io.github.terraria.client.view.Textures;

public record TextureFixture(String texture, float width, float height,float centerX, float centerY) {
    private static String DEFAULT_MISSING_PATH = "missing.png";
    public TextureFixture() { this(DEFAULT_MISSING_PATH,0f, 0f,0f,0f); }
}
