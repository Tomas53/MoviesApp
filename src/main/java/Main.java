import repositories.DatabaseRepo;
import repositories.UserRepo;
import utils.JsonFileConverter;

public class Main {
    public static void main(String[] args) {
        JsonFileConverter jsonFileConverter = new JsonFileConverter();
        //jsonFileConverter.getAllUsersFromJson().forEach(System.out::println);
        jsonFileConverter.getAllMoviesFromJson();
        jsonFileConverter.getAllPlatformsFromJson();
        DatabaseRepo databaseRepo=new DatabaseRepo();
//        databaseRepo.deleteTables();
        UserRepo userRepo=new UserRepo();
        databaseRepo.createTables();
//        for (int i = 0; i <jsonFileConverter.getAllUsersFromJson().size() ; i++) {
//            userRepo.insertUser(jsonFileConverter.getAllUsersFromJson().get(i));
//            System.out.println(jsonFileConverter.getAllUsersFromJson().get(i));
//        }

    }
}
