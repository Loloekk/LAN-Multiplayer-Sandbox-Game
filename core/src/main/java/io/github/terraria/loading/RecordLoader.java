package io.github.sandboxGame.loading;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.List;

public class RecordLoader {
    private static final ObjectMapper mapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public static <T> List<T> loadList(String jsonName, Class<T> clazz) {
        try (InputStream is = RecordLoader.class.getClassLoader().getResourceAsStream(jsonName)) {
            return mapper.readValue(
                is,
                mapper.getTypeFactory().constructCollectionType(List.class, clazz)
            );
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static <T> T loadRecord(String jsonName, Class<T> clazz) {
        try (InputStream is = RecordLoader.class.getClassLoader().getResourceAsStream(jsonName)) {
            return mapper.readValue(is, clazz);
        } catch (Exception e) { throw new RuntimeException(e); }
    }
}
