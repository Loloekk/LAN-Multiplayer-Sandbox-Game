package io.github.terraria.logic;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.InputStreamReader;
import java.io.Reader;
import java.util.function.Consumer;

public class JsonLoader {
    public static void loadJson(String resourcePath, Consumer<JsonObject> consumer) {
        try (Reader reader = new InputStreamReader(
            JsonLoader.class.getResourceAsStream(resourcePath))) {
            JsonArray blocksArray = new Gson().fromJson(reader, JsonArray.class);
            for (JsonElement element : blocksArray)
                consumer.accept(element.getAsJsonObject());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
