package gov.iti.link.business.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Vector;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.mappers.ContactMapper;
import gov.iti.link.business.mappers.GroupMapper;
import gov.iti.link.business.mappers.UserMapper;
import gov.iti.link.persistence.DAOs.UserDao;
import gov.iti.link.persistence.DAOs.UserDaoImp;
import gov.iti.link.persistence.entities.ContactEntity;
import gov.iti.link.persistence.entities.GroupEntity;
import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.mappers.InvitationMapper;

import gov.iti.link.persistence.entities.InvitationEntity;
import gov.iti.link.persistence.entities.UserEntity;

public class UserServiceImp extends UnicastRemoteObject implements UserService {

    Vector<ClientServices> allClients = new Vector<>();
    public Vector<UserDTO> allOnlineUser = new Vector<>();

    public UserServiceImp() throws RemoteException {
        super();
    }

    private final UserDao userDAO = new UserDaoImp();
    private final UserMapper userMapper = new UserMapper();
    private final ContactMapper contactMapper = new ContactMapper();
    private final GroupMapper groupMapper = new GroupMapper();

    @Override
    public UserDTO save(UserDTO user) {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        this.userDAO.save(userEntity);
        return user;
    }

    @Override
    public UserDTO findByPhone(String phone) throws RemoteException {
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
    public InvitationDTO sendInvite(String fromPhone, String toPhone) throws RemoteException {
        InvitationEntity invitationEntity = this.userDAO.saveInvitation(fromPhone, toPhone);
        if (invitationEntity == null)
            return null;
        InvitationDTO invitationDTO = InvitationMapper.entityToDTO(invitationEntity);

        for (ClientServices client : allClients) {
            if (client.getUserDTO().getPhone().equals(toPhone)) {
                System.out
                        .println(fromPhone + " is sending a msg to " + toPhone + " :" + client.getUserDTO().getName());
                client.notifyInvitation(invitationDTO);
            }

        }

        return invitationDTO;

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

    @Override
    public Vector<ContactDto> getAllContacts(String userPhone) throws RemoteException {
        Vector<ContactEntity> allContactEntities = this.userDAO.getAllContacts(userPhone);
        Vector<ContactDto> allContactsContactDtos = new Vector<>();
        for (ContactEntity contactEntity : allContactEntities) {
            allContactsContactDtos.add(mapFromUsertoContact(contactEntity));
        }
        return allContactsContactDtos;
    }

    @Override
    public int addContact(String userPhone, String friendPhone) {
        return this.userDAO.addContact(userPhone, friendPhone);
    }

    private ContactDto mapFromUsertoContact(ContactEntity contactEntity) {
        ContactDto contactDto = contactMapper.entityToDTO(contactEntity);
        UserDTO userDTO;
        try {
            userDTO = findByPhone(contactDto.getPhoneNumber());

            contactDto.setName(userDTO.getName());
            contactDto.setImage(userDTO.getPicture());
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return contactDto;
    }

    @Override
    public int updateUser(UserDTO user) throws RemoteException {
        UserEntity userEntity = userMapper.dtoToEntity(user);
        this.userDAO.updateUser(userEntity);
        return 1;
    }

    @Override
    public void userLoggedIn(ClientServices clientServices, UserDTO userDTO) throws RemoteException {
        System.out.println("user" + userDTO.getPhone());
        synchronized (this) {
            allClients.add(clientServices);
            allOnlineUser.add(userDTO);
        }

        for (ClientServices client : allClients)
            if (!client.equals(clientServices))
                client.notifyContactStatus(userDTO, true);
            else {
                for (UserDTO onlineUserDTO : allOnlineUser)
                    client.notifyContactStatus(onlineUserDTO, true);
            }

    }

    @Override
    public void userLoggedOut(ClientServices clientServices, UserDTO userDTO) throws RemoteException {
        System.out.println("user" + userDTO.getPhone());
        synchronized (this) {
            allClients.remove(clientServices);
            allOnlineUser.remove(userDTO);
        }
        for (ClientServices client : allClients)
            if (!client.equals(clientServices))
                client.notifyContactStatus(userDTO, false);

    }

    public void acceptInvite(InvitationDTO invite) throws RemoteException {
        this.userDAO.addContact(invite.getFromPhone(), invite.getToPhone());
        this.userDAO.addContact(invite.getToPhone(), invite.getFromPhone());
        this.userDAO.deleteInvite(invite.getId());
        for (ClientServices client : allClients) {
            if (client.getUserDTO().getPhone().equals(invite.getFromPhone())) {
                client.notifyNewContact(invite.getToPhone());
                System.out.println(invite.getToPhone() + " you accept invitation from " + invite.getFromPhone());
            } else if (client.getUserDTO().getPhone().equals(invite.getToPhone())) {
                client.notifyNewContact(invite.getFromPhone());
                System.out.println(invite.getToPhone() + " accepted your invitation");

            }
        }
    }

    @Override
    public void rejectInvite(InvitationDTO invite) throws RemoteException {
        this.userDAO.deleteInvite(invite.getId());

    }

    @Override
    public void sendMessage(String fromPhone, String message, String toPhone) throws RemoteException {
        for (ClientServices client : allClients) {
            if (toPhone.equals(client.getUserDTO().getPhone())) {
                client.tellMessage(message, fromPhone);
            }
        }

    }

    @Override
    public GroupDto createGroup(GroupDto groupDto) throws RemoteException {
        return groupMapper.entityToDTO(this.userDAO.createGroup(groupDto));

    }

    @Override
    public GroupDto getGroup(int id) throws RemoteException {
    GroupDto groupDto = groupMapper.entityToDTO(this.userDAO.getGroup(id));
    groupDto.setAllMembers(getAllGroupMembers(groupDto.getGroupId()));
    return groupDto;

    }

    @Override
    public int addMemberToGroup(GroupDto groupDto, String memberPhone) throws RemoteException {
        groupDto.addMember(memberPhone);
        this.userDAO.addMemberToGroup(groupDto.getGroupId(), memberPhone);
        Vector<String> allMembers = getAllGroupMembers(groupDto.getGroupId());
        for (ClientServices client : allClients) {
            if (memberPhone.equals(client.getUserDTO().getPhone())) {
                client.notifyYouAddedToGroup(groupDto);
            } else if (allMembers.contains(memberPhone)) {
                client.notifyNewMember(groupDto, memberPhone);
            }
        }
        return 0;
    }

    @Override
    public Vector<GroupDto> getAllGroups(String mamberPhone) throws RemoteException {
        Vector<GroupEntity> allGroupsEntities = this.userDAO.getAllGroups(mamberPhone);
        Vector<GroupDto> allGroupsDtos = new Vector<>();
        for (GroupEntity groupEntity : allGroupsEntities) {
            GroupDto groupDto = groupMapper.entityToDTO(groupEntity);
            groupDto.setAllMembers(getAllGroupMembers(groupDto.getGroupId()));
            allGroupsDtos.add(groupDto);
        }
        // todo get all members and add in list
        return allGroupsDtos;
    }

    @Override
    public Vector<String> getAllGroupMembers(int id) throws RemoteException {
        // Vector<String> allMembersPhone = this.userDAO.getAllGroupMembers(id);
        // Vector<UserDTO> allGroupMembers = new Vector<>();
        // for(String phone:allMembersPhone){
        // allGroupMembers.add(findByPhone(phone));
        // }
        return this.userDAO.getAllGroupMembers(id);
    }

    @Override
    public void sendMessageToGroup(String fromPhone, int groupId, String message, Vector<String> toPhone)
            throws RemoteException {
        for (ClientServices client : allClients) {
            if (toPhone.contains(client.getUserDTO().getPhone()) && !client.getUserDTO().getPhone().equals(fromPhone)) {
                client.tellMessageFromGroup(message, groupId, fromPhone);
            }
        }

    }

    @Override
    public void sendFileToGroup(String fromPhone, int groupId, byte[] filebytes, String filePath, int length,
            Vector<String> toPhone) throws RemoteException {
        for (ClientServices client : allClients) {
            if (toPhone.contains(client.getUserDTO().getPhone()) && !client.getUserDTO().getPhone().equals(fromPhone)) {

                //int index = allOnlineUser.indexOf(user);

                File path = new File(filePath);
                try {
                    FileOutputStream out = new FileOutputStream(path);
                    byte[] data = filebytes;
                    System.out.println("Byte data " + data.length);
                    out.write(data);
                    out.flush();
                    System.out.println("Done Writing data");
                    client.tellFileFromGroup(filePath, groupId, data, fromPhone);
                    out.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }

    }

    @Override
    public void sendFile(String fromPhone, byte[] filebytes, String filePath, int length, String toPhone) throws RemoteException {
  
    for (UserDTO user : allOnlineUser) {
        if (toPhone.contains(user.getPhone()))
         {
            
            int index = allOnlineUser.indexOf(user);
            
            File path = new File(filePath);
            try {
                FileOutputStream out = new FileOutputStream(path);
                byte [] data = filebytes;
                System.out.println("Byte data " + data.length);
                out.write(data);
                out.flush();
                System.out.println("Done Writing data");
                allClients.get(index).tellFile(filePath ,data ,fromPhone);
                out.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
    }
        
    }

}


