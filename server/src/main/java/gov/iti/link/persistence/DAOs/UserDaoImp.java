package gov.iti.link.persistence.DAOs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.Optional;
import java.util.Vector;

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
            try {
                preparedStatement.setBlob(4, new FileInputStream(user.getPicture().substring(6)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
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

    @Override
    public Optional<UserEntity> findByPhone(String phone) {
        final String SQL = "select * from users where phoneNumber=? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, phone);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    Integer id = resultSet.getInt(1);
                    String userName = resultSet.getString(3);
                    String email = resultSet.getString(4);
                    String picture = convertFromBlobtoString(resultSet.getBlob(5));
                    String password = resultSet.getString(6);
                    String gender = resultSet.getString(7);
                    String country = resultSet.getString(8);
                    Date datOfBirth = resultSet.getDate(9);
                    String bio = resultSet.getString(10);
                    UserEntity userEntity = new UserEntity(phone, userName, email, picture, gender, country, datOfBirth,
                            bio, password);
                    return Optional.of(userEntity);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        return Optional.empty();
    }

    private String convertFromBlobtoString(Blob blob) {
        byte[] bdata;
        String text = new String();
        try {
            bdata = blob.getBytes(1, (int) blob.length());
            text = new String(bdata);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    
    private String convertFromBlobtoString2(Blob blob) {
        byte[] bdata;
        String text = new String();
        
        try {
            //bdata = blob.getBytes(1, (int) blob.length());
            text = Base64.getEncoder().encodeToString(blob.getBytes(1, (int) blob.length()));;
            //text = new String(bdata);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return text;
    }

    @Override
    public Vector<UserEntity> getAllUsers() {
        Vector<UserEntity> allUsers = new Vector<>();
        final String SQL = "select * from users ";
        try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(SQL)) {
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String phoneNum = resultSet.getString(2);
                String userName = resultSet.getString(3);
                String email = resultSet.getString(4);
                String picture = convertFromBlobtoString(resultSet.getBlob(5));
                String password = resultSet.getString(6);
                String gender = resultSet.getString(7);
                String country = resultSet.getString(8);
                Date datOfBirth = resultSet.getDate(9);
                String bio = resultSet.getString(10);
                UserEntity userEntity = new UserEntity(phoneNum, userName, email, picture, gender, country, datOfBirth,
                        bio, password);
                allUsers.add(userEntity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

}
