package repositories;

import dto.UserPlatformDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class UserPlatformRepo {

    public void insertUserPlatforms(List<UserPlatformDto> userPlatforms) {
        String sql = "INSERT INTO user_platform (id, userId, platformId) VALUES (?, ?, ?)";

        try (Connection connection = DatabaseRepo.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            for (UserPlatformDto userPlatform : userPlatforms) {
                statement.setInt(1, userPlatform.getId());
                statement.setInt(2, userPlatform.getUserId());
                statement.setInt(3, userPlatform.getPlatformId());

                statement.executeUpdate();
            }

        } catch (SQLException e) {
            System.err.println("Error inserting user_platform records: " + e.getMessage());
            e.printStackTrace();
        }
    }
}