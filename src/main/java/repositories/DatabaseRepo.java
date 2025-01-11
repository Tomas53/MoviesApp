package repositories;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseRepo {
    //insert DB name, user name and password
    private static final String URL = "jdbc:postgresql://localhost:5432/moviesApp";
    private static final String USER = "******";
    private static final String PASSWORD = "******";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public void createTables() {
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();

            // Create the movies table first
            String createMoviesTable = """
                CREATE TABLE IF NOT EXISTS movies (
                    movie_id SERIAL PRIMARY KEY,
                    title VARCHAR(50),
                    genre VARCHAR(20),
                    director VARCHAR(100),
                    price DOUBLE PRECISION,
                    movieCast TEXT,
                    duration INT,
                    imdb_rating DOUBLE PRECISION,
                    metascore INT
                );
            """;
            System.out.println("[LOG] Executing SQL: " + createMoviesTable);
            statement.executeUpdate(createMoviesTable);

            // Create the platforms table next
            String createPlatformsTable = """
                CREATE TABLE IF NOT EXISTS platforms (
                    platform_id SERIAL PRIMARY KEY,
                    platform_name VARCHAR(100),
                    subscription_type VARCHAR(50),
                    price INT,
                    movie_id INT REFERENCES movies(movie_id)
                );
            """;
            System.out.println("[LOG] Executing SQL: " + createPlatformsTable);
            statement.executeUpdate(createPlatformsTable);

            // Create the users table last
            String createUsersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id SERIAL PRIMARY KEY,
                    name VARCHAR(255),
                    date_of_birth DATE,
                    email VARCHAR(100),
                    street VARCHAR(255),
                    platform_id INT REFERENCES platforms(platform_id)
                );
            """;
            System.out.println("[LOG] Executing SQL: " + createUsersTable);
            statement.executeUpdate(createUsersTable);

            System.out.println("[LOG] Tables created successfully.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Table creation failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void deleteTables() {
        String sql = """
            DROP TABLE IF EXISTS users CASCADE;
            DROP TABLE IF EXISTS platforms CASCADE;
            DROP TABLE IF EXISTS movies CASCADE;
        """;
        try (Connection connection = getConnection()) {
            Statement statement = connection.createStatement();
            System.out.println("[LOG] Executing SQL: " + sql);
            statement.executeUpdate(sql);
            System.out.println("[LOG] Tables dropped successfully.");
        } catch (SQLException e) {
            System.err.println("[ERROR] Table deletion failed: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
