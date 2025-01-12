import repositories.*;
import utils.DatabaseSeeder;
import utils.JsonFileConverter;

public class Main {
    public static void main(String[] args) {
        JsonFileConverter jsonFileConverter = new JsonFileConverter();
        DatabaseSeeder databaseSeeder = new DatabaseSeeder(jsonFileConverter);
        DatabaseRepo databaseRepo = new DatabaseRepo();
        UserRepo userRepo = new UserRepo();
        PlatformRepo platformRepo = new PlatformRepo();
        MovieRepo movieRepo = new MovieRepo();
        UserPlatformRepo userPlatformRepo = new UserPlatformRepo();
        PlatformMoviesRepo platformMoviesRepo = new PlatformMoviesRepo();

        databaseRepo.createTables();
        databaseSeeder.seedDbTablesWithDummyData(movieRepo, platformRepo, userRepo, userPlatformRepo, platformMoviesRepo);
    }
}