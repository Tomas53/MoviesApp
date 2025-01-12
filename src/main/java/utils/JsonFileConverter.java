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

    public void seedDbTablesWithDummyData(MovieRepo movieRepo,
                                                  PlatformRepo platformRepo, UserRepo userRepo,
                                                  UserPlatformRepo userPlatformRepo, PlatformMoviesRepo platformMoviesRepo) {
        try {
            // Seed movies table
            var movies = getAllMoviesFromJson();
            movieRepo.insertMovies(movies);
            System.out.println("Movies table seeded successfully!");

            // Seed platforms table
            var platforms = getAllPlatformsFromJson();
            platformRepo.insertPlatforms(platforms);
            System.out.println("Platforms table seeded successfully!");

            // Seed users table
            var users = getAllUsersFromJson();
            for (var user : users) {
                userRepo.insertUser(user);
            }
            System.out.println("Users table seeded successfully!");

            // Seed user platforms table
            var userPlatforms = getAllUserPlatformsFromJson();
            userPlatformRepo.insertUserPlatforms(userPlatforms);
            System.out.println("User platforms table seeded successfully!");

            // Seed platform movies table
            var platformMovies = getAllPlatformMoviesFromJson();
            platformMoviesRepo.insertPlatformMovies(platformMovies);
            System.out.println("latform movies table seeded successfully!");

        } catch (Exception e) {
            System.err.println("Error seeding tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}