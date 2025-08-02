/*
package io.github.terraria.logic.block;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

*/
/**
 * Loads blocks from a JSON file.
 *//*

public class BlockJsonLoader {
    public static List<Block> loadAllBlocks() {
        FileHandle file = Gdx.files.internal("blocks.json");
        if (!file.exists()) {
            throw new RuntimeException("no blocks.json");
        }
        List<Block> blocks = new ArrayList<>();
        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(file);

        for (JsonValue blockJson : root) {
            Block block = new Block(
                blockJson.getInt("id"),
                blockJson.getString("name"),
                blockJson.getString("texture"),
                blockJson.getString("behavior")
            );
            blocks.add(block);
        }
        return blocks;
    }
}
*/
