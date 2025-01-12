import repositories.DatabaseRepo;
import repositories.MovieRepo;
import repositories.UserRepo;
import utils.JsonFileConverter;

public class Main {
    public static void main(String[] args) {
        JsonFileConverter jsonFileConverter = new JsonFileConverter();
        DatabaseRepo databaseRepo=new DatabaseRepo();
//        UserRepo userRepo = new UserRepo();
//
//        databaseRepo.createTables();
//
//        var movies = jsonFileConverter.getAllMoviesFromJson();
//        MovieRepo movieRepo = new MovieRepo();
//
//        try {
//            movieRepo.insertMovies(movies);
//            System.out.println("Movies table seeded successfully!");
//        } catch (Exception e) {
//            System.err.println("Error seeding movies table: " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        for (int i = 0; i < jsonFileConverter.getAllUsersFromJson().size(); i++) {
//            userRepo.insertUser(jsonFileConverter.getAllUsersFromJson().get(i));
//        }
    }
}
