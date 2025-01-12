package repositories;

import dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepo {
    public void insertUser(UserDto userDto){
        String SQL= """
                insert into Users (name, date_of_birth, email, platformid values (?, ?, ?, ?)
                """;

        try(Connection connection=DatabaseRepo.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            preparedStatement.setString(1, userDto.getName());
            preparedStatement.setDate(2, java.sql.Date.valueOf(userDto.getDateOfBirth()));
            preparedStatement.setString(3, userDto.getEmail());
            preparedStatement.setInt(4, userDto.getPlatformId());

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
