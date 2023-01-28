package gov.iti.link.persistence.DAOs;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import gov.iti.link.persistence.ConnectionManager;
import gov.iti.link.persistence.entities.UserEntity;

public class UserDaoImp implements UserDao {

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final Connection connection = connectionManager.getConnection();

    @Override
    public UserEntity save(UserEntity user) {
        final String SQL = "insert into users " +
                "(phoneNumber ,userName , email, picture, userPassword,gender,country,dateOfBirth,bio)" +
                " values (?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, user.getPhone());
            preparedStatement.setString(2, user.getName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPicture());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.setString(6, user.getGender());
            preparedStatement.setString(7, user.getCountry());
            preparedStatement.setDate(8, user.getDate());
            preparedStatement.setString(9, user.getBio());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

}
