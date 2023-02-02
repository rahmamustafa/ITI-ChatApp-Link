package gov.iti.link.persistence.DAOs;

import java.util.Optional;
import java.util.Vector;

import gov.iti.link.persistence.entities.UserEntity;

public interface UserDao {
    public UserEntity save(UserEntity user);
    public Optional<UserEntity> findByPhone(String phone);
    public Vector<UserEntity> getAllUsers();
    boolean updateUser(UserEntity user);

}
