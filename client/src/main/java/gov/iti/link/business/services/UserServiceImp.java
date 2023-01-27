package gov.iti.link.business.services;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.mappers.UserMapper;
import gov.iti.link.persistence.DAOs.UserDao;
import gov.iti.link.persistence.DAOs.UserDaoImp;
import gov.iti.link.persistence.entities.UserEntity;

public class UserServiceImp implements UserService {

    private final UserDao userDAO = new UserDaoImp();
    private final UserMapper userMapper = new UserMapper();

    @Override
    public UserDTO save(UserDTO user) {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        this.userDAO.save(userEntity);
        return user;
    }

}
