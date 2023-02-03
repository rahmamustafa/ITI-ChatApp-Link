package gov.iti.link.business.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Optional;
import java.util.Vector;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.mappers.ContactMapper;
import gov.iti.link.business.mappers.UserMapper;
import gov.iti.link.persistence.DAOs.UserDao;
import gov.iti.link.persistence.DAOs.UserDaoImp;
import gov.iti.link.persistence.entities.ContactEntity;
import gov.iti.link.persistence.entities.UserEntity;

public class UserServiceImp extends UnicastRemoteObject implements UserService {

    public UserServiceImp() throws RemoteException {
        super();
    }

    private final UserDao userDAO = new UserDaoImp();
    private final UserMapper userMapper = new UserMapper();
    private final ContactMapper contactMapper = new ContactMapper();


    @Override
    public UserDTO save(UserDTO user) {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        this.userDAO.save(userEntity);
        return user;
    }

    @Override
    public UserDTO findByPhone(String phone) throws RemoteException {
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
  
    @Override
    public int sendInvite(String fromPhone,String toPhone) throws RemoteException {
        return this.userDAO.saveInvitation(fromPhone,toPhone);
    }

   

    @Override
    public Vector<ContactDto> getAllContacts(String userPhone) throws RemoteException {
        Vector<ContactEntity> allContactEntities = this.userDAO.getAllContacts(userPhone);
        Vector<ContactDto> allContactsContactDtos = new Vector<>();
        for(ContactEntity contactEntity : allContactEntities){
            allContactsContactDtos.add(mapFromUsertoContact(contactEntity));
        }
        return allContactsContactDtos;
    }

    @Override
    public int addContact(String userPhone, String friendPhone) {
        return this.userDAO.addContact(userPhone,friendPhone);
    }


    private ContactDto mapFromUsertoContact(ContactEntity contactEntity){
        ContactDto contactDto = contactMapper.entityToDTO(contactEntity);
        UserDTO userDTO;
        try {
            userDTO = findByPhone(contactDto.getPhoneNumber());
        
            contactDto.setName(userDTO.getName());
        contactDto.setImageUrl(userDTO.getPicture());
    } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contactDto;
    }

    @Override
    public boolean updateUser(UserDTO user) throws RemoteException {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        this.userDAO.updateUser(userEntity);
        return false;
    }

    

}
