package gov.iti.link.business.services;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.persistence.entities.ContactEntity;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Vector;

public interface UserService extends Remote {
    UserDTO save(UserDTO user) throws RemoteException;

    UserDTO findByPhone(String phone) throws RemoteException;

    Vector<UserDTO> getAllUsers() throws RemoteException;

    int sendInvite(String fromPhone, String toPhone) throws RemoteException;

    int addContact(String userPhone, String friendPhone) throws RemoteException;

    Vector<ContactDto> getAllContacts(String userPhone) throws RemoteException;
}
