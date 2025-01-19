package utils;

import dto.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import repositories.*;
import services.MovieAnalyticsService;

import java.util.List;
import java.util.Scanner;
import java.util.*;

@Getter
@RequiredArgsConstructor
public class CLIInterface {
    private final Scanner scanner = new Scanner(System.in);
    private final DatabaseRepo databaseRepo = new DatabaseRepo();
    private final MovieAnalyticsService movieAnalyticsService = new MovieAnalyticsService();

    public void start() {
        while (true) {
            System.out.print("""
                    Welcome to the movies app CLI interface
                    This program can do several things like:
                    1 initial setup
                    2 uninstall itself
                    3 add data
                    4 get data
                    5 use the movie analytics service to examine the movies added
                    6 quit
                    Please enter a number of an action above:""");

            int choice = getValidInput(1, 6);

            if (choice == 6) {
                System.out.println("Exiting application...");
                scanner.close();
                return;
            }

            handleMainMenuChoice(choice);
        }
    }

    private void handleMainMenuChoice(int choice) {
        switch (choice) {
            case 1 -> databaseRepo.createTables();
            case 2 -> databaseRepo.deleteTables();
            case 3 -> handleDataAddition();
            case 4 -> databaseRepo.printAllData();
            case 5 -> handleAnalytics();
            default -> System.out.println("Invalid choice");
        }
    }

    private void handleAnalytics() {

        while (true) {
            System.out.print("""
                The movies analytics service can do several things like:
                1 get top rated movies
                2 get average movie rating for a platform
                3 get movies under a duration
                4 get most popular genre
                5 get director with highest average IMDb rating
                6 get movies by genre
                7 exit
                Please enter a number of an action:""");

            int choice = getValidInput(1, 7);
            switch (choice) {
                case 1 -> movieAnalyticsService.getTopRatedMovies().forEach(System.out::println);
                case 2 -> {
                    System.out.print("Which platform: ");
                    String platform = scanner.next();
                    System.out.println(movieAnalyticsService.getAverageMovieRatingForSpecificPlatform(platform));
                }
                case 3 -> {
                    System.out.print("Which duration: ");
                    int duration = getValidInput(0, Integer.MAX_VALUE);
                    movieAnalyticsService.getMoviesByDuration(duration).forEach(System.out::println);
                }
                case 4 -> {
                    System.out.println("Most popular genre:");
                    System.out.println(movieAnalyticsService.getMostPopularGenre());
                    System.out.println();
                }
                case 5 -> {
                    System.out.println("director with highest average IMDb rating:");
                    System.out.println(movieAnalyticsService.getDirectorWithHigestAverageIMDbRating());
                    System.out.println();
                }
                case 6 -> {
                    System.out.println("Provide your chosen genre:");
                    String chosenGenre = scanner.next();
                    movieAnalyticsService.getMoviesByGenre(chosenGenre).forEach(System.out::println);
                    System.out.println();
                }

                default -> System.out.println("Invalid choice");

            }
            if (choice == 7) {
                System.out.println("Exiting analytics service...");
                return;
            }
        }
    }

    private void handleDataAddition() {
        System.out.print("""
                What type of data do you want to add?
                1 user
                2 platform
                3 movie
                4 user to platform link
                5 platform to movies link
                6 automatically read data from json's
                Please enter a number:""");

        int choice = getValidInput(1, 6);

        switch (choice) {
            case 1 -> addUser();
            case 2 -> addPlatform();
            case 3 -> addMovie();
            case 4 -> addUserPlatformLink();
            case 5 -> addPlatformMovieLink();
            case 6 -> seedDatabase();
            default -> System.out.println("Invalid choice");
        }
    }

    private int getValidInput(int min, int max) {
        while (true) {
            try {
                int input = scanner.nextInt();
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %d and %d: ", min, max);
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private void addUser() {
        System.out.print("Enter user name: ");
        String name = scanner.next();
        System.out.print("Enter user email: ");
        String email = scanner.next();

        new UserRepo().insertUser(UserDto.builder()
                .name(name)
                .email(email)
                .build());
    }

    private void addPlatform() {
        System.out.print("Enter platform ID: ");
        int id = getValidInput(1, Integer.MAX_VALUE);
        System.out.print("Enter platform name: ");
        String name = scanner.next();
        System.out.print("Enter subscription type: ");
        String subscriptionType = scanner.next();
        System.out.print("Enter subscription price: ");
        double price = scanner.nextDouble();

        var platform = PlatformDto.builder()
                .platformId(id)
                .platformName(name)
                .subscriptionType(subscriptionType)
                .price(price)
                .build();

        new PlatformRepo().insertPlatforms(List.of(platform));
    }

    private void addMovie() {
        System.out.print("What is the id of the movie?: ");
        int movieId = getValidInput(1, Integer.MAX_VALUE);
        System.out.print("What is the title of the movie?: ");
        String title = scanner.next();
        System.out.print("What is the genre of the movie?: ");
        String genre = scanner.next();
        System.out.print("What is the director of the movie?: ");
        String director = scanner.next();
        System.out.print("What is the price of the movie if it was bought single?: ");
        double moviePrice = scanner.nextDouble();
        System.out.print("What is the duration of the movie?: ");
        int duration = getValidInput(1, Integer.MAX_VALUE);
        System.out.print("What is the imdb rating of the movie?: ");
        int imdbRating = getValidInput(0, 10);
        System.out.print("What is the metascore of the movie?: ");
        int metascore = getValidInput(0, 100);

        var movie = MovieDto.builder()
                .movieId(movieId)
                .title(title)
                .genre(genre)
                .director(director)
                .price(moviePrice)
                .duration(duration)
                .imdbRating(imdbRating)
                .metascore(metascore)
                .build();

        new MovieRepo().insertMovies(List.of(movie));
    }

    private void addUserPlatformLink() {
        System.out.print("What is the id of the user to platform link?: ");
        int linkId = getValidInput(1, Integer.MAX_VALUE);
        System.out.print("What is the id of the user?: ");
        int userId = getValidInput(1, Integer.MAX_VALUE);
        System.out.print("What is the id of the platform?: ");
        int platformId = getValidInput(1, Integer.MAX_VALUE);

        var link = UserPlatformDto.builder()
                .id(linkId)
                .userId(userId)
                .platformId(platformId)
                .build();

        new UserPlatformRepo().insertUserPlatforms(List.of(link));
    }

    private void addPlatformMovieLink() {
        System.out.print("What is the id of the platform to movie link?: ");
        int linkId = getValidInput(1, Integer.MAX_VALUE);
        System.out.print("What is the id of the platform?: ");
        int platformId = getValidInput(1, Integer.MAX_VALUE);
        System.out.print("What is the id of the movie?: ");
        int movieId = getValidInput(1, Integer.MAX_VALUE);

        var link = PlatformMovieDto.builder()
                .id(linkId)
                .platformId(platformId)
                .movieId(movieId)
                .build();

        new PlatformMoviesRepo().insertPlatformMovies(List.of(link));
    }

    private void seedDatabase() {
        new DatabaseSeeder(new JsonFileConverter()).seedDbTablesWithDummyData(
                new MovieRepo(),
                new PlatformRepo(),
                new UserRepo(),
                new UserPlatformRepo(),
                new PlatformMoviesRepo()
        );
        System.out.println("Database seeded successfully");
    }
}