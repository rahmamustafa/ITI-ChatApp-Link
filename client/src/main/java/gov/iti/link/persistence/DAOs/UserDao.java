package gov.iti.link.persistence.DAOs;

import gov.iti.link.persistence.entities.UserEntity;

public interface UserDao {
    public UserEntity save(UserEntity user);
}
