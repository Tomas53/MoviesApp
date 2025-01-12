package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dto.*;
import repositories.*;

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

            return objectMapper.readValue(inputStream, new TypeReference<>() {
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
            return objectMapper.readValue(inputStream, new TypeReference<>() {
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
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading platform.json file", e);
        }
    }

    public List<UserPlatformDto> getAllUserPlatformsFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("user_platform.json")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File user_platform.json not found in resources");
            }
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading user_platform.json file", e);
        }
    }

    public List<PlatformMovieDto> getAllPlatformMoviesFromJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule

        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("platform_movies.json")) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File platform_movies.json not found in resources");
            }
            return objectMapper.readValue(inputStream, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException("Error reading platform_movies.json file", e);
        }
    }
}