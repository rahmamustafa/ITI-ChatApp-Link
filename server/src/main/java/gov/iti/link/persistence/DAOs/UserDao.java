package gov.iti.link.persistence.DAOs;

import java.util.Optional;

import gov.iti.link.persistence.entities.UserEntity;

public interface UserDao {
    public UserEntity save(UserEntity user);
    public Optional<UserEntity> findByPhone(String phone);
}
