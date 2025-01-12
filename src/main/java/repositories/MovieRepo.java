package repositories;

import dto.MovieDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
}