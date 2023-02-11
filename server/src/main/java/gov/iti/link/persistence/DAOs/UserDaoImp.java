package gov.iti.link.persistence.DAOs;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.persistence.ConnectionManager;
import gov.iti.link.persistence.entities.ContactEntity;
import gov.iti.link.persistence.entities.GroupEntity;
import gov.iti.link.persistence.entities.GroupUsersEntity;
import gov.iti.link.persistence.entities.InvitationEntity;
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
            preparedStatement.setBinaryStream(4, new ByteArrayInputStream(user.getPicture()),user.getPicture().length);
            // try {
            //     preparedStatement.setBlob(4, new FileInputStream(user.getPicture().substring(6)));
            // } catch (FileNotFoundException e) {
            //     e.printStackTrace();
            // }
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
                    byte[] picture = resultSet.getBytes(5);
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
            // bdata = blob.getBytes(1, (int) blob.length());
            text = Base64.getEncoder().encodeToString(blob.getBytes(1, (int) blob.length()));
            ;
            // text = new String(bdata);
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
                // String picture = convertFromBlobtoString(resultSet.getBlob(5));
                byte[] picture = resultSet.getBinaryStream(5).readAllBytes();
                String password = resultSet.getString(6);
                String gender = resultSet.getString(7);
                String country = resultSet.getString(8);
                Date datOfBirth = resultSet.getDate(9);
                String bio = resultSet.getString(10);
                UserEntity userEntity = new UserEntity(phoneNum, userName, email, picture, gender, country, datOfBirth,
                        bio, password);
                allUsers.add(userEntity);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    @Override
    public InvitationEntity saveInvitation(String fromPhone, String toPhone) {

        InvitationEntity invitation = null ; 
        ResultSet resultSet;
        int result = -1;
        final String SQL = "insert into invitations " +
                "(fromPhone, toPhone)" +
                " values (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, fromPhone);
            preparedStatement.setString(2, toPhone);
            result = preparedStatement.executeUpdate();
            System.out.println("result >>" + result);
            if(result != -1){
         
                resultSet = preparedStatement.executeQuery("select * from invitations where "+
                " fromPhone= " + fromPhone + " and toPhone= " + toPhone 
                );
                resultSet.next();
                int id = resultSet.getInt(1);
                String from = resultSet.getString(2);
                String to = resultSet.getString(3);
                Date date = resultSet.getDate(4);
                invitation = new InvitationEntity(id,from,to,date);
                System.out.println("From User Dao InvEntity>" + invitation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invitation;

    }

    @Override
    public int addContact(String userPhone, String friendPhone) {
        int result = -1;
        final String SQL = "insert into contacts " +
                "(userPhone, friendPhone)" +
                " values (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, userPhone);
            preparedStatement.setString(2, friendPhone);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public Vector<ContactEntity> getAllContacts(String phone) {
        Vector<ContactEntity> allContacts = new Vector<>();
        final String SQL = "select * from Contacts where userPhone=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, phone);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String userPhone = resultSet.getString(2);
                String friendPhone = resultSet.getString(3);
                ContactEntity contactEntity = new ContactEntity(userPhone, friendPhone);
                allContacts.add(contactEntity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allContacts;
    }

    
    
    public int updateUser(UserEntity user) {
        int result = -1;
        final String SQL = "update users set " +
                "phoneNumber=? ,userName=?, email=?, bio=?, userPassword=?,gender=?,country=?,dateOfBirth=? " +
                " where phoneNumber=? ";

                try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)){
                    preparedStatement.setString(1, user.getPhone());
                    preparedStatement.setString(2, user.getName());
                    System.out.println(user.getName());
                    preparedStatement.setString(3, user.getEmail());
                    preparedStatement.setString(4, user.getBio());
                    preparedStatement.setString(5, user.getPassword());
                    preparedStatement.setString(6, user.getGender());
                    preparedStatement.setString(7, user.getCountry());
                    preparedStatement.setDate(8, user.getDate());
                    
                    //preparedStatement.setString(9, user.getPicture());
                    preparedStatement.setString(9, user.getPhone());
                    // preparedStatement.setString(1, user.getName());
                    // preparedStatement.setString(2, user.getPhone());
                    // System.out.println(user.getPhone());
                    // System.out.println(user.getName());
                     
                    result = preparedStatement.executeUpdate();
                    System.out.println(result);
                }
                catch (SQLException e) {
                    e.printStackTrace();
                } 


        return result;
    }


    

    public List<InvitationEntity> getUserInvitations(String userPhone) {
        List<InvitationEntity> invitations = new ArrayList<InvitationEntity>();
        final String SQL = "select * from invitations where toPhone=? ";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, userPhone);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String fromPhone = resultSet.getString(2);
                    String toPhone = resultSet.getString(3);
                    Date date = resultSet.getDate(4);

                    invitations.add(new InvitationEntity(id, fromPhone, toPhone, date));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invitations;
    }

    @Override
    public int deleteInvite(int invitationID) {
        int result = -1;
        final String SQL = "delete from invitations " +
                "where id= ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, invitationID);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public GroupEntity createGroup(String groupName) {
        GroupEntity groupEntity = new GroupEntity();
        final String SQL = "insert into allgroups " +
                "(groupName)" +
                " values (?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL,Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, groupName);
            if(preparedStatement.executeUpdate()>0){
                groupEntity.setGroupName(groupName);
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if(resultSet.next())
                    groupEntity.setGroupId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupEntity;
    }

    @Override
    public GroupEntity getGroup(int groupId) {
       GroupEntity groupEntity;
        final String SQL = "select * from allgroups where id=? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, groupId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt(1);
                    String groupName = resultSet.getString(2);
                    groupEntity = new GroupEntity(id,groupName);
                    return groupEntity;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int addMemberToGroup(int groupId , String memberPhone) {
        int result = -1;
        final String SQL = "insert into groupUsers " +
                "(memberPhone,groupId)" +
                " values (?,?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, memberPhone);
            preparedStatement.setInt(2, groupId);
            result = preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
        
    }

    @Override
    public Vector<GroupEntity> getAllGroups(String mamberPhone) {
        Vector<GroupEntity> allGroups = new Vector<>();
        final String SQL = "select id,groupname from groupUsers,allgroups"+ 
                            " where memberPhone= ? and allgroups.id=groupUsers.groupid";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, mamberPhone);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Integer id = resultSet.getInt(1);
                String groupName = resultSet.getString(2);
                GroupEntity groupEntity = new GroupEntity(id, groupName);
                allGroups.add(groupEntity);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allGroups;
    }

    @Override
    public Vector<String> getAllGroupMembers(int groupId) {
        Vector<String> allMembers = new Vector<>();
        final String SQL = "select memberPhone from groupusers where groupid=?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setInt(1, groupId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                String memberPhone = resultSet.getString(1);
                allMembers.add(memberPhone);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allMembers;
    }

}
