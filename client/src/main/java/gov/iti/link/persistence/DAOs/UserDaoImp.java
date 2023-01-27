package gov.iti.link.persistence.DAOs;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import gov.iti.link.persistence.ConnectionManager;
import gov.iti.link.persistence.entities.UserEntity;

public class UserDaoImp implements UserDao{

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();
    private final Connection connection = connectionManager.getConnection();
    
    @Override
    public UserEntity save(UserEntity user) {
        final String SQL = "INSERT INTO users (phone, name) VALUE (?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, user.getPhone());
            preparedStatement.setString(2, user.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    
}
