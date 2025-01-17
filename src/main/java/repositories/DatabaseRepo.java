package repositories;

import dto.MovieDto;
import dto.PlatformDto;
import dto.UserDto;
import lombok.Cleanup;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseRepo {
    private static final String URL = "jdbc:postgresql://localhost:5432/moviesApp";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void createTables() {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            String sql = """
                        CREATE TABLE IF NOT EXISTS movies (
                            movieId SERIAL PRIMARY KEY,
                            title VARCHAR(50),
                            genre VARCHAR(20),
                            director VARCHAR(100),
                            price DOUBLE PRECISION,
                            duration INT,
                            imdbRating DOUBLE PRECISION,
                            metascore INT
                        );
                    
                        CREATE TABLE IF NOT EXISTS platforms (
                            platformId SERIAL PRIMARY KEY,
                            platformName VARCHAR(100),
                            subscriptionType VARCHAR(50),
                            price INT
                        );
                    
                        CREATE TABLE IF NOT EXISTS users (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(255),
                            email VARCHAR(100)
                        );
                    
                        create table if not exists user_platform (
                            id serial primary key,
                            userId int references users(id),
                            platformId int references platforms(platformId)
                        );
                    
                        create table if not exists platform_movies (
                            id serial primary key,
                            platformId int references platforms(platformId),
                            movieId int references movies(movieId)
                        );
                    """;
            System.out.println("[LOG] " + sql);
            statement.executeUpdate(sql);
            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTables() {
        String sql = """
                    DROP TABLE IF EXISTS users CASCADE;
                    DROP TABLE IF EXISTS platforms CASCADE;
                    DROP TABLE IF EXISTS movies CASCADE;
                    drop table if exists user_platform cascade;
                    drop table if exists platform_movies cascade;
                """;

        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            System.out.println("[LOG] " + sql);
            statement.executeUpdate(sql);
            System.out.println("Tables dropped successfully.");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void printAllData() {
        String sql = """
                SELECT
                u.id AS user_id,
                        u.name AS user_name,
                u.email AS user_email,
                        p.platformId AS platform_id,
                p.platformName AS platform_name,
                        p.subscriptionType AS subscription_type,
                p.price AS platform_price,
                        m.movieId AS movie_id,
                m.title AS movie_title,
                        m.genre AS movie_genre,
                m.director AS movie_director,
                        m.price AS movie_price,
                m.duration AS movie_duration,
                        m.imdbRating AS imdb_rating,
                m.metascore AS movie_metascore
                FROM users u
                LEFT JOIN user_platform up ON u.id = up.userId
                RIGHT JOIN platforms p ON up.platformId = p.platformId
                LEFT JOIN platform_movies pm ON p.platformId = pm.platformId
                RIGHT JOIN movies m ON pm.movieId = m.movieId
                """;
        try (var connection = getConnection()) {
            @Cleanup var statement = connection.createStatement();
            @Cleanup var result = statement.executeQuery(sql);
            var users = parseResultSet(result);
            displayUsers(users);
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving data", e);
        }
    }

    private void displayUsers(List<UserDto> users) {
        if (users.isEmpty()) {
            System.out.println("No users found in the database.");
            return;
        }

        for (UserDto user : users) {
            System.out.println("\n=== User Information ===");
            System.out.println("ID: " + user.getId());
            System.out.println("Name: " + user.getName());
            System.out.println("Email: " + user.getEmail());

            if (user.getPlatforms().isEmpty()) {
                System.out.println("No platforms associated with this user.");
                continue;
            }

            System.out.println("\n--- Subscribed Platforms ---");
            for (PlatformDto platform : user.getPlatforms()) {
                System.out.println("\nPlatform Details:");
                System.out.println("ID: " + platform.getPlatformId());
                System.out.println("Name: " + platform.getPlatformName());
                System.out.println("Price: $" + String.format("%.2f", platform.getPrice()));
                System.out.println("Subscription Type: " + platform.getSubscriptionType());

                if (platform.getMovies().isEmpty()) {
                    System.out.println("No movies available on this platform.");
                    continue;
                }

                System.out.println("\nAvailable Movies:");
                for (MovieDto movie : platform.getMovies()) {
                    System.out.println("\nMovie Details:");
                    System.out.println("ID: " + movie.getMovieId());
                    System.out.println("Title: " + movie.getTitle());
                    System.out.println("Genre: " + movie.getGenre());
                    System.out.println("Director: " + movie.getDirector());
                    System.out.println("Duration: " + movie.getDuration() + " minutes");
                    System.out.println("Price: $" + String.format("%.2f", movie.getPrice()));
                    System.out.println("IMDB Rating: " + movie.getImdbRating() + "/10");
                    System.out.println("Metascore: " + movie.getMetascore() + "/100");
                }
            }
            System.out.println("\n" + "=".repeat(30));
        }
    }

    private static List<UserDto> parseResultSet(ResultSet resultSet) throws SQLException {
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