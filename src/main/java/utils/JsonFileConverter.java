package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.UserDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonFileConverter {
    public List<UserDto> getAllUsers() {
        ObjectMapper objectMapper = new ObjectMapper();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movieApp.json")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File movieApp.json not found in resources");
            }
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading movieApp.json file", e);
        }
    }
}