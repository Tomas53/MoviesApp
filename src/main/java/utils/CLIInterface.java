package utils;

import dto.*;
import repositories.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CLIInterface {
    public void cliInterface() {
        Scanner scanner = new Scanner(System.in);
        DatabaseRepo databaseRepo = new DatabaseRepo();
        while (true) {
            System.out.print("""
                    Welcome to the movies app CLI interface \n
                    This program can do several things like: \n
                    1 initial setup \n
                    2 uninstall itself \n
                    3 add data \n
                    4 get data \n
                    5 use an analytics service to examine the data added \n
                    Please enter a number of an action above: 
                    """);
            int answer = scanner.nextInt();

            switch (answer) {
                case 1:
                    databaseRepo.createTables();
                    break;
                case 2:
                    databaseRepo.deleteTables();
                    break;
                case 3:
                    boolean isGoing = true;
                    while (isGoing) {
                        System.out.println("""
                                What type of data do you want to add? \n
                                1 user \n
                                2 platforms \n
                                3 movies \n
                                4 user to platform link \n
                                5 platform to movies link \n
                                6 automatically read data from json's \n
                                As before please enter a number of an action above: 
                                """);
                        int answer2 = scanner.nextInt();
                        switch (answer2) {
                            case 1:
                                UserRepo userRepo = new UserRepo();
                                System.out.println("What is the name of the user?: ");
                                String name = scanner.next();
                                System.out.println("What is the email of the user?: ");
                                String email = scanner.next();
                                userRepo.insertUser(UserDto.builder().name(name).email(email).build());
                                isGoing = false;
                                break;
                            case 2:
                                PlatformRepo platformRepo = new PlatformRepo();
                                System.out.println("What is the id of the platform?: ");
                                int id = scanner.nextInt();
                                System.out.println("What is the name of the platform?: ");
                                String platformName = scanner.next();
                                System.out.println("What is the subscription type of the platform?: ");
                                String subscriptionType = scanner.next();
                                System.out.println("What is the subscription price of the platform?: ");
                                double price = scanner.nextDouble();
                                List<PlatformDto> list = new ArrayList<>();
                                list.add(PlatformDto.builder()
                                        .platformId(id).platformName(platformName)
                                        .subscriptionType(subscriptionType).price(price).build());
                                platformRepo.insertPlatforms(list);
                                isGoing = false;
                                break;
                            case  3:
                                MovieRepo movieRepo = new MovieRepo();
                                System.out.println("What is the id of the movie?: ");
                                int movieId = scanner.nextInt();
                                System.out.println("What is the title of the movie?: ");
                                String title = scanner.next();
                                System.out.println("What is the genre of the movie?: ");
                                String genre = scanner.next();
                                System.out.println("What is the director of the movie?: ");
                                String director = scanner.next();
                                System.out.println("What is the price of the movie if it was bought single of the movie?: ");
                                double moviePrice = scanner.nextDouble();
                                System.out.println("What is the duration of the movie?: ");
                                int duration = scanner.nextInt();
                                System.out.println("What is the imdb rating of the movie?: ");
                                int imdbRating = scanner.nextInt();
                                System.out.println("What is the metascore of the movie?: ");
                                int metascore = scanner.nextInt();
                                List<MovieDto> movieList = new ArrayList<>();
                                movieList.add(MovieDto.builder()
                                        .movieId(movieId).title(title).genre(genre)
                                        .director(director).price(moviePrice).duration(duration)
                                        .imdbRating(imdbRating).metascore(metascore).build());
                                movieRepo.insertMovies(movieList);
                                isGoing = false;
                                break;
                            case 4:
                                UserPlatformRepo userPlatformRepo = new UserPlatformRepo();
                                System.out.println("What is the id of the user to platform link?: ");
                                int userPlatformId = scanner.nextInt();
                                System.out.println("What is the id of the user?: ");
                                int userId = scanner.nextInt();
                                System.out.println("What is the id of the platform?: ");
                                int platformId = scanner.nextInt();
                                List<UserPlatformDto> userPlatformlist = new ArrayList<>();
                                userPlatformlist.add(UserPlatformDto.builder().id(userPlatformId).userId(userId).platformId(platformId).build());
                                userPlatformRepo.insertUserPlatforms(userPlatformlist);
                                isGoing = false;
                                break;
                            case 5:
                                PlatformMoviesRepo platformMoviesRepo = new PlatformMoviesRepo();
                                System.out.println("What is the id of the platform to movie link?: ");
                                int platformMovieId = scanner.nextInt();
                                System.out.println("What is the id of the platform?: ");
                                int platformId2 = scanner.nextInt();
                                System.out.println("What is the id of the movie?: ");
                                int movieId2 = scanner.nextInt();
                                List<PlatformMovieDto> platformMovielist = new ArrayList<>();
                                platformMovielist.add(PlatformMovieDto.builder().id(platformMovieId).platformId(platformId2).movieId(movieId2).build());
                                platformMoviesRepo.insertPlatformMovies(platformMovielist);
                                isGoing = false;
                                break;
                            case 6:
                                new DatabaseSeeder(new JsonFileConverter()).seedDbTablesWithDummyData(new MovieRepo(),
                                        new PlatformRepo(),
                                        new UserRepo(),
                                        new UserPlatformRepo(),
                                        new PlatformMoviesRepo());
                                System.out.println("Automatic insertion was successful");
                                isGoing = false;
                                break;
                            default:
                                System.out.println("The inputted number does not correspond to an action");
                        }
                    }
                case 4:

            }
        }
    }
}