package utils;

import dto.*;
import repositories.*;
import services.MovieAnalyticsService;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static repositories.DatabaseRepo.getConnection;

public class CLIInterface {
    public void cliInterface() {
        Scanner scanner = new Scanner(System.in);
        DatabaseRepo databaseRepo = new DatabaseRepo();
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
                    Please enter a number of an action above: """);
            int answer = scanner.nextInt();
            switch (answer) {
                case 1:
                    databaseRepo.createTables();
                    break;
                case 2:
                    databaseRepo.deleteTables();
                    break;
                case 3:
                    System.out.print("""
                            What type of data do you want to add?
                            1 user
                            2 platform
                            3 movie
                            4 user to platform link
                            5 platform to movies link
                            6 automatically read data from json's
                            As before please enter a number of an action above: """);
                    int answer2 = scanner.nextInt();
                    switch (answer2) {
                        case 1:
                            UserRepo userRepo = new UserRepo();
                            System.out.print("What is the name of the user?: ");
                            String name = scanner.next();
                            System.out.print("What is the email of the user?: ");
                            String email = scanner.next();
                            userRepo.insertUser(UserDto.builder().name(name).email(email).build());
                            break;
                        case 2:
                            PlatformRepo platformRepo = new PlatformRepo();
                            System.out.print("What is the id of the platform?: ");
                            int id = scanner.nextInt();
                            System.out.print("What is the name of the platform?: ");
                            String platformName = scanner.next();
                            System.out.print("What is the subscription type of the platform?: ");
                            String subscriptionType = scanner.next();
                            System.out.print("What is the subscription price of the platform?: ");
                            double price = scanner.nextDouble();
                            List<PlatformDto> list = new ArrayList<>();
                            list.add(PlatformDto.builder()
                                    .platformId(id).platformName(platformName)
                                    .subscriptionType(subscriptionType).price(price).build());
                            platformRepo.insertPlatforms(list);
                            break;
                        case 3:
                            MovieRepo movieRepo = new MovieRepo();
                            System.out.print("What is the id of the movie?: ");
                            int movieId = scanner.nextInt();
                            System.out.print("What is the title of the movie?: ");
                            String title = scanner.next();
                            System.out.print("What is the genre of the movie?: ");
                            String genre = scanner.next();
                            System.out.print("What is the director of the movie?: ");
                            String director = scanner.next();
                            System.out.print("What is the price of the movie if it was bought single of the movie?: ");
                            double moviePrice = scanner.nextDouble();
                            System.out.print("What is the duration of the movie?: ");
                            int duration = scanner.nextInt();
                            System.out.print("What is the imdb rating of the movie?: ");
                            int imdbRating = scanner.nextInt();
                            System.out.print("What is the metascore of the movie?: ");
                            int metascore = scanner.nextInt();
                            List<MovieDto> movieList = new ArrayList<>();
                            movieList.add(MovieDto.builder()
                                    .movieId(movieId).title(title).genre(genre)
                                    .director(director).price(moviePrice).duration(duration)
                                    .imdbRating(imdbRating).metascore(metascore).build());
                            movieRepo.insertMovies(movieList);
                            break;
                        case 4:
                            UserPlatformRepo userPlatformRepo = new UserPlatformRepo();
                            System.out.print("What is the id of the user to platform link?: ");
                            int userPlatformId = scanner.nextInt();
                            System.out.print("What is the id of the user?: ");
                            int userId = scanner.nextInt();
                            System.out.print("What is the id of the platform?: ");
                            int platformId = scanner.nextInt();
                            List<UserPlatformDto> userPlatformlist = new ArrayList<>();
                            userPlatformlist.add(UserPlatformDto.builder().id(userPlatformId).userId(userId).platformId(platformId).build());
                            userPlatformRepo.insertUserPlatforms(userPlatformlist);
                            break;
                        case 5:
                            PlatformMoviesRepo platformMoviesRepo = new PlatformMoviesRepo();
                            System.out.print("What is the id of the platform to movie link?: ");
                            int platformMovieId = scanner.nextInt();
                            System.out.print("What is the id of the platform?: ");
                            int platformId2 = scanner.nextInt();
                            System.out.print("What is the id of the movie?: ");
                            int movieId2 = scanner.nextInt();
                            List<PlatformMovieDto> platformMovielist = new ArrayList<>();
                            platformMovielist.add(PlatformMovieDto.builder().id(platformMovieId).platformId(platformId2).movieId(movieId2).build());
                            platformMoviesRepo.insertPlatformMovies(platformMovielist);
                            break;
                        case 6:
                            new DatabaseSeeder(new JsonFileConverter()).seedDbTablesWithDummyData(new MovieRepo(),
                                    new PlatformRepo(),
                                    new UserRepo(),
                                    new UserPlatformRepo(),
                                    new PlatformMoviesRepo());
                            System.out.println("Automatic insertion was successful");
                            break;
                        default:
                            System.out.println("The inputted number does not correspond to an action");
                            break;
                    }
                    break;
                case 4:
                    String sql = """
                            SELECT\s
                                u.id AS user_id, -- Alias for user ID
                                u.name AS user_name,\s
                                u.email AS user_email,
                                p.platformId AS platform_id, -- Alias for platform ID
                                p.platformName AS platform_name,\s
                                p.subscriptionType AS subscription_type,\s
                                p.price AS platform_price,
                                m.movieId AS movie_id, -- Alias for movie ID
                                m.title AS movie_title,\s
                                m.genre AS movie_genre,\s
                                m.director AS movie_director,\s
                                m.price AS movie_price,\s
                                m.duration AS movie_duration,\s
                                m.imdbRating AS imdb_rating,\s
                                m.metascore AS movie_metascore
                            FROM\s
                                users u
                            LEFT JOIN\s
                                user_platform up ON u.id = up.userId
                            RIGHT JOIN\s
                                platforms p ON up.platformId = p.platformId
                            LEFT JOIN\s
                                platform_movies pm ON p.platformId = pm.platformId
                            RIGHT JOIN\s
                                movies m ON pm.movieId = m.movieId;
                            """;
                    try (Connection conection = getConnection()) {
                        Statement statement = conection.createStatement();
                        System.out.println("[LOG] " + sql);
                        ResultSet result = statement.executeQuery(sql);
                        List<UserDto> resultList = mapToUserDtoList(result);
                        System.out.println("users:");
                        for (UserDto userDto : resultList) {
                            System.out.println("id " + userDto.getId());
                            System.out.println("name " + userDto.getName());
                            System.out.println("platforms");
                            for (PlatformDto platformDto : userDto.getPlatforms()) {
                                System.out.println("id " + platformDto.getPlatformId());
                                System.out.println("name " + platformDto.getPlatformName());
                                System.out.println("price " + platformDto.getPrice());
                                System.out.println("subscription type " + platformDto.getSubscriptionType());
                                System.out.println("movies");
                                for (MovieDto movieDto : platformDto.getMovies()) {
                                    System.out.println("id " + movieDto.getMovieId());
                                    System.out.println("title " + movieDto.getTitle());
                                    System.out.println("price " + movieDto.getPrice());
                                    System.out.println("director " + movieDto.getDirector());
                                    System.out.println("duration " + movieDto.getDuration());
                                    System.out.println("genre " + movieDto.getGenre());
                                    System.out.println("imdb rating " + movieDto.getImdbRating());
                                    System.out.println("metascore " + movieDto.getMetascore());
                                }
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case 5:
                    MovieAnalyticsService movieAnalyticsService = new MovieAnalyticsService();
                    System.out.print("""
                            The movies analytics service can do several thing like:
                            1 get top rated movies
                            2 get average movie rating for a platform
                            3 get movies under a duration
                            Please enter a number of an action: """);
                    int answer4 = scanner.nextInt();
                    switch (answer4) {
                        case 1:
                            movieAnalyticsService.getTopRatedMovies().forEach(System.out::println);
                            break;
                        case 2:
                            System.out.print("Which platform: ");
                            String platform = scanner.next();
                            System.out.println(movieAnalyticsService.getAverageMovieRatingForSpecificPlatform(platform));
                            break;
                        case 3:
                            System.out.print("Which duration: ");
                            int duration = scanner.nextInt();
                            movieAnalyticsService.getMoviesByDuration(duration).forEach(System.out::println);
                            break;
                        default:
                            System.out.print("The inputted number does not correspond to an action");
                            break;
                    }
                    break;
                case 6:
                    return;
                default:
                    System.out.println("The inputted number does not correspond to an action");
                    break;
            }
        }
    }

    private static List<UserDto> mapToUserDtoList(ResultSet resultSet) throws SQLException {
        // Map to store Users with their associated Platforms and Movies
        Map<Integer, UserDto> userMap = new HashMap<>();
        Map<Integer, PlatformDto> platformMap = new HashMap<>();

        while (resultSet.next()) {
            // Extract data from the ResultSet
            int userId = resultSet.getInt("user_id");
            String userName = resultSet.getString("user_name");
            String userEmail = resultSet.getString("user_email");

            int platformId = resultSet.getInt("platform_id");
            String platformName = resultSet.getString("platform_name");
            String subscriptionType = resultSet.getString("subscription_type");
            double platformPrice = resultSet.getDouble("platform_price");

            int movieId = resultSet.getInt("movie_id");
            String movieTitle = resultSet.getString("movie_title");
            String movieGenre = resultSet.getString("movie_genre");
            String movieDirector = resultSet.getString("movie_director");
            double moviePrice = resultSet.getDouble("movie_price");
            int movieDuration = resultSet.getInt("movie_duration");
            double imdbRating = resultSet.getDouble("imdb_rating");
            double metascore = resultSet.getDouble("movie_metascore");

            // Create or retrieve UserDto
            UserDto user = userMap.computeIfAbsent(userId, id -> UserDto.builder()
                    .id(id)
                    .name(userName)
                    .email(userEmail)
                    .platforms(new ArrayList<>())
                    .build());

            // Create or retrieve PlatformDto
            PlatformDto platform = platformMap.computeIfAbsent(platformId, id -> PlatformDto.builder()
                    .platformId(id)
                    .platformName(platformName)
                    .subscriptionType(subscriptionType)
                    .price(platformPrice)
                    .movies(new ArrayList<>())
                    .build());

            // Add PlatformDto to UserDto if not already added
            if (!user.getPlatforms().contains(platform)) {
                user.getPlatforms().add(platform);
            }

            // Create MovieDto and add it to PlatformDto
            MovieDto movie = MovieDto.builder()
                    .movieId(movieId)
                    .title(movieTitle)
                    .genre(movieGenre)
                    .director(movieDirector)
                    .price(moviePrice)
                    .duration(movieDuration)
                    .imdbRating(imdbRating)
                    .metascore(metascore)
                    .build();

            if (!platform.getMovies().contains(movie)) {
                platform.getMovies().add(movie);
            }
        }

        // Return the list of UserDto objects
        return new ArrayList<>(userMap.values());
    }
}