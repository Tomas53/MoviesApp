package repositories;

import dto.MovieDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MovieRepo {

    public void insertMovies(List<MovieDto> movies) {
        String sql = "INSERT INTO Movies (movieId, title, genre, director, price, duration, imdbRating, metascore) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseRepo.getConnection();
             PreparedStatement movieStmt = connection.prepareStatement(sql)) {

            for (MovieDto movie : movies) {
                movieStmt.setInt(1, movie.getMovieId());
                movieStmt.setString(2, movie.getTitle());
                movieStmt.setString(3, movie.getGenre());
                movieStmt.setString(4, movie.getDirector());
                movieStmt.setDouble(5, movie.getPrice());
                movieStmt.setInt(6, movie.getDuration());
                movieStmt.setDouble(7, movie.getImdbRating());
                movieStmt.setDouble(8, movie.getMetascore());

                movieStmt.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error inserting movies: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<MovieDto> getAllMovies() {
        String SQL = """
                SELECT * FROM movies 
                """;

        try (Connection connection = DatabaseRepo.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(SQL);
            ResultSet rs = ps.executeQuery();
            List<MovieDto> movieList = new ArrayList<>();
            while (rs.next()) {
                int movieId = rs.getInt("movieid");
                String title = rs.getString("title");
                String genre = (rs.getString("genre"));
                String director = (rs.getString("director"));
                Double price = (rs.getDouble("price"));
                int duration = (rs.getInt("duration"));
                Double imdbRating = (rs.getDouble("imdbrating"));
                Double metascore = (rs.getDouble("metascore"));


                MovieDto movieDto = new MovieDto(movieId, title, genre, director, price, duration, imdbRating, metascore);
                movieList.add(movieDto);
            }

            return movieList;
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving accounts: " + e.getMessage(), e);
        }

    }

    public List<MovieDto> getMoviesByPlatform(String platformName) {
        String getPlatformIdSQL = "SELECT platformid FROM platforms WHERE platformname = ?";

        String getMoviesSQL = """
                SELECT * 
                FROM movies 
                WHERE movieid IN (
                    SELECT movieid 
                    FROM platform_movies 
                    WHERE platformid = ?
                )
                """;

        try (Connection connection = DatabaseRepo.getConnection()) {
            PreparedStatement platformIdStmt = connection.prepareStatement(getPlatformIdSQL);
            platformIdStmt.setString(1, platformName);
            ResultSet platformIdRs = platformIdStmt.executeQuery();

            if (!platformIdRs.next()) {
                return new ArrayList<>();
            }

            int platformId = platformIdRs.getInt("platformid");

            PreparedStatement moviesStmt = connection.prepareStatement(getMoviesSQL);
            moviesStmt.setInt(1, platformId);
            ResultSet moviesRs = moviesStmt.executeQuery();

            List<MovieDto> movieList = new ArrayList<>();

            while (moviesRs.next()) {
                int movieId = moviesRs.getInt("movieid");
                String title = moviesRs.getString("title");
                String genre = moviesRs.getString("genre");
                String director = moviesRs.getString("director");
                Double price = moviesRs.getDouble("price");
                int duration = moviesRs.getInt("duration");
                Double imdbRating = moviesRs.getDouble("imdbrating");
                Double metascore = moviesRs.getDouble("metascore");

                MovieDto movieDto = new MovieDto(movieId, title, genre, director, price, duration, imdbRating, metascore);
                movieList.add(movieDto);
            }
            return movieList;

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving movies for platform: " + platformName, e);
        }
    }

}