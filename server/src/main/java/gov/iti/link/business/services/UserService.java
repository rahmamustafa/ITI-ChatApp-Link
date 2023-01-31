package gov.iti.link.business.services;

import gov.iti.link.business.DTOs.UserDTO;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface UserService extends Remote{
    UserDTO save(UserDTO user) throws RemoteException;
    UserDTO findByPhone(String phone) throws RemoteException;
}
