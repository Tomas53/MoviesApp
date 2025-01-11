import utils.JsonFileConverter;

public class Main {
    public static void main(String[] args) {
        JsonFileConverter jsonFileConverter = new JsonFileConverter();
        jsonFileConverter.getAllUsersFromJson().forEach(System.out::println);
        jsonFileConverter.getAllMoviesFromJson();
        jsonFileConverter.getAllPlatformsFromJson();
    }
}
