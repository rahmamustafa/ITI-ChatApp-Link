package gov.iti.link.business.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;
import java.util.Vector;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.mappers.UserMapper;
import gov.iti.link.persistence.DAOs.UserDao;
import gov.iti.link.persistence.DAOs.UserDaoImp;
import gov.iti.link.persistence.entities.UserEntity;

public class UserServiceImp extends UnicastRemoteObject implements UserService {

    public UserServiceImp() throws RemoteException {
        super();
    }

    private final UserDao userDAO = new UserDaoImp();
    private final UserMapper userMapper = new UserMapper();

    @Override
    public UserDTO save(UserDTO user) {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        this.userDAO.save(userEntity);
        return user;
    }

    @Override
    public UserDTO findByPhone(String phone) {
        Optional<UserEntity> userEntity = this.userDAO.findByPhone(phone);
        if(userEntity.isPresent())
            return userMapper.entityToDTO(userEntity.get());      
        else
            return null; 
    }

    @Override
    public Vector<UserDTO> getAllUsers() throws RemoteException {
        Vector<UserEntity> allUserEntities = this.userDAO.getAllUsers();
        Vector<UserDTO> allUserDTOs = new Vector<>();
        for(UserEntity userEntity : allUserEntities){
            allUserDTOs.add(userMapper.entityToDTO(userEntity));
        }
        return allUserDTOs;
    }

}
