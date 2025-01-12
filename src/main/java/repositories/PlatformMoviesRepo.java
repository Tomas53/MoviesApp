package repositories;

import dto.PlatformMovieDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PlatformMoviesRepo {
    public void insertPlatformMovies(List<PlatformMovieDto> platformMovies) {
        String sql = "INSERT INTO platform_movies (id, platformId, movieId) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseRepo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (PlatformMovieDto platformMovie : platformMovies) {
                statement.setInt(1, platformMovie.getId());
                statement.setInt(2, platformMovie.getPlatformId());
                statement.setInt(3, platformMovie.getMovieId());

                statement.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error inserting platform_movies records: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
