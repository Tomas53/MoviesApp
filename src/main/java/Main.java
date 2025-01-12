import repositories.DatabaseRepo;
import repositories.MovieRepo;
import repositories.PlatformRepo;
import repositories.UserRepo;
import utils.JsonFileConverter;

public class Main {
    public static void main(String[] args) {
        JsonFileConverter jsonFileConverter = new JsonFileConverter();
        DatabaseRepo databaseRepo = new DatabaseRepo();
        UserRepo userRepo = new UserRepo();
        PlatformRepo platformRepo = new PlatformRepo();
        MovieRepo movieRepo = new MovieRepo();

        databaseRepo.createTables();
        seedDbTablesWithDummyData(jsonFileConverter, movieRepo, platformRepo, userRepo);
    }

    private static void seedDbTablesWithDummyData(JsonFileConverter jsonFileConverter, MovieRepo movieRepo, PlatformRepo platformRepo, UserRepo userRepo) {
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

        } catch (Exception e) {
            System.err.println("Error seeding tables: " + e.getMessage());
            e.printStackTrace();
        }
    }
}