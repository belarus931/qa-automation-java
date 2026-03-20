package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import models.User;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonReader {

    private static final ObjectMapper mapper = new ObjectMapper();

    /**
     * Читает объект из JSON-файла
     */
    public static <T> T readFromFile(String filePath, Class<T> clazz) {
        try {
            return mapper.readValue(new File(filePath), clazz);
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON from file: " + filePath, e);
        }
    }

    /**
     * Читает список объектов из JSON-файла
     */
    public static <T> List<T> readListFromFile(String filePath, Class<T> clazz) {
        try {
            return mapper.readValue(new File(filePath),
                    mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read JSON list from file: " + filePath, e);
        }
    }

    /**
     * Читает объект из resources
     */
    public static <T> T readFromResources(String resourcePath, Class<T> clazz) {
        String fullPath = "src/test/resources/" + resourcePath;
        return readFromFile(fullPath, clazz);
    }
}