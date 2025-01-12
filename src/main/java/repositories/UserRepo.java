package repositories;

import dto.UserDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UserRepo {
    public void insertUser(UserDto userDto){
        String SQL = """
                INSERT INTO Users (name, email) VALUES (?, ?)
                """;

        try(Connection connection=DatabaseRepo.getConnection()){
            PreparedStatement preparedStatement=connection.prepareStatement(SQL);
            preparedStatement.setString(1, userDto.getName());
            preparedStatement.setString(2, userDto.getEmail());

            preparedStatement.executeUpdate();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
