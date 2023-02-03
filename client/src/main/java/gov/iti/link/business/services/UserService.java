package gov.iti.link.business.services;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.UserDTO;

public interface UserService extends Remote{
    UserDTO save(UserDTO user) throws RemoteException;
    UserDTO findByPhone(String phone) throws RemoteException;
    Vector<UserDTO> getAllUsers() throws RemoteException;
    int sendInvite(String fromPhone,String toPhone) throws RemoteException;
    int addContact(String userPhone,String friendPhone) throws RemoteException;
    Vector<ContactDto> getAllContacts(String userPhone) throws RemoteException;
    boolean updateUser(UserDTO user) throws RemoteException;
    
}
