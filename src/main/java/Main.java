import repositories.DatabaseRepo;
import utils.JsonFileConverter;

public class Main {
    public static void main(String[] args) {
        JsonFileConverter jsonFileConverter = new JsonFileConverter();
        jsonFileConverter.getAllUsersFromJson().forEach(System.out::println);
        jsonFileConverter.getAllMoviesFromJson();
        jsonFileConverter.getAllPlatformsFromJson();
        DatabaseRepo databaseRepo=new DatabaseRepo();
        databaseRepo.deleteTables();
        databaseRepo.createTables();
    }
}
