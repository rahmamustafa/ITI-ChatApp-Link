package gov.iti.link.business.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.mappers.InvitationMapper;
import gov.iti.link.business.mappers.UserMapper;
import gov.iti.link.persistence.DAOs.UserDao;
import gov.iti.link.persistence.DAOs.UserDaoImp;
import gov.iti.link.persistence.entities.InvitationEntity;
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
        if (userEntity.isPresent())
            return userMapper.entityToDTO(userEntity.get());
        else
            return null;
    }

    @Override
    public Vector<UserDTO> getAllUsers() throws RemoteException {
        Vector<UserEntity> allUserEntities = this.userDAO.getAllUsers();
        Vector<UserDTO> allUserDTOs = new Vector<>();
        for (UserEntity userEntity : allUserEntities) {
            allUserDTOs.add(userMapper.entityToDTO(userEntity));
        }
        return allUserDTOs;
    }

    @Override
    public int sendInvite(String fromPhone, String toPhone) throws RemoteException {
        return this.userDAO.saveInvitation(fromPhone, toPhone);
    }

    @Override
    public List<InvitationDTO> getInvitations(String userPhone) throws RemoteException {

        List<InvitationDTO> invitations = new ArrayList<InvitationDTO>();
        List<InvitationEntity> invitationEntities = this.userDAO.getUserInvitations(userPhone);

        for (InvitationEntity invite : invitationEntities) {
            invitations.add(InvitationMapper.entityToDTO(invite));
        }
        return invitations;
    }

}
