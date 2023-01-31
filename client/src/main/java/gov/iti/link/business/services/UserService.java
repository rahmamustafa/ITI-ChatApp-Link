package gov.iti.link.business.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import gov.iti.link.business.DTOs.UserDTO;

public interface UserService extends Remote{
    UserDTO save(UserDTO user) throws RemoteException;
    UserDTO findByPhone(String phone) throws RemoteException;
}
