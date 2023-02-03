package gov.iti.link.business.services;

import gov.iti.link.business.DTOs.UserDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface UserService extends Remote{
    UserDTO save(UserDTO user) throws RemoteException;
    UserDTO findByPhone(String phone) throws RemoteException;
    Vector<UserDTO> getAllUsers() throws RemoteException;
    int sendInvite(String fromPhone,String toPhone) throws RemoteException;
    // ContactDTO addContact(String fromPhone, String toPhone)
  
}
