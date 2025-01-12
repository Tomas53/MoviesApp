package utils;

import repositories.*;

public class DatabaseSeeder {
    private final JsonFileConverter jsonFileConverter;

    public DatabaseSeeder(JsonFileConverter jsonFileConverter) {
        this.jsonFileConverter = jsonFileConverter;
    }

    public void seedDbTablesWithDummyData(MovieRepo movieRepo,
                                          PlatformRepo platformRepo,
                                          UserRepo userRepo,
                                          UserPlatformRepo userPlatformRepo,
                                          PlatformMoviesRepo platformMoviesRepo) {
        try {
            // Seed movies table
            var movies = jsonFileConverter.getAllMoviesFromJson();
            movieRepo.insertMovies(movies);
            System.out.println("Movies table seeded successfully!");

            // Seed platforms table
            var platforms = jsonFileConverter.getAllPlatformsFromJson();
            platformRepo.insertPlatforms(platforms);
            System.out.println("Platforms table seeded successfully!");

            // Seed users table
            var users = jsonFileConverter.getAllUsersFromJson();
            for (var user : users) {
                userRepo.insertUser(user);
            }
            System.out.println("Users table seeded successfully!");

            // Seed user platforms table
            var userPlatforms = jsonFileConverter.getAllUserPlatformsFromJson();
            userPlatformRepo.insertUserPlatforms(userPlatforms);
            System.out.println("User platforms table seeded successfully!");

            // Seed platform movies table
            var platformMovies = jsonFileConverter.getAllPlatformMoviesFromJson();
            platformMoviesRepo.insertPlatformMovies(platformMovies);
            System.out.println("Platform movies table seeded successfully!");

        } catch (Exception e) {
            System.err.println("Error seeding tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}