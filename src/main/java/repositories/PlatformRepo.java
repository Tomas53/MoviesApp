package repositories;

import dto.PlatformDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class PlatformRepo {
    public void insertPlatforms(List<PlatformDto> platforms) {
        String sql = "INSERT INTO platforms (platformId, platformName, subscriptionType, price) VALUES (?, ?, ?, ?)";

        try (Connection connection = DatabaseRepo.getConnection();
             PreparedStatement platform = connection.prepareStatement(sql)) {

            for (PlatformDto movie : platforms) {
                platform.setInt(1, movie.getPlatformId());
                platform.setString(2, movie.getPlatformName());
                platform.setString(3, movie.getSubscriptionType());
                platform.setDouble(4, movie.getPrice());

                platform.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error inserting platforms: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
