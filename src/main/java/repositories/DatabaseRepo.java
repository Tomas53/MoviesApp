package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRepo {
    private static final String URL = "jdbc:postgresql://localhost:5432/moviesApp";
    private static final String USER = "*";
    private static final String PASSWORD = "*";

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
}
