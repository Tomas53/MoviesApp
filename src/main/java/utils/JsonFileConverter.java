package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.MovieDto;
import dto.PlatformDto;
import dto.UserDto;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class JsonFileConverter {
    public List<UserDto> getAllUsersFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("user.json")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File user.json not found in resources");
            }

            return objectMapper.readValue(inputStream, new TypeReference<List<UserDto>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading user.json file", e);
        }
    }

    public List<MovieDto> getAllMoviesFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("movies.json")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File movies.json not found in resources");
            }
            return objectMapper.readValue(inputStream, new TypeReference<List<MovieDto>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading movies.json file", e);
        }
    }

    public List<PlatformDto> getAllPlatformsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("platform.json")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File platform.json not found in resources");
            }
            return objectMapper.readValue(inputStream, new TypeReference<List<PlatformDto>>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading platform.json file", e);
        }
    }
}