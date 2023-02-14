package gov.iti.link.business.services;

import java.rmi.Remote;
import java.rmi.RemoteException;

import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;

public interface ClientServices extends Remote {

    void notifyContactStatus(UserDTO contactDto, boolean isActive ) throws RemoteException;
    void tellMessage(String message,String fromPhone)throws RemoteException;
    void tellFileFromGroup(String file , int groupId ,byte[] data,String fromPhone) throws RemoteException;
    void notifyInvitation(InvitationDTO invitationDTO) throws RemoteException;
    UserDTO getUserDTO() throws RemoteException;
    void notifyNewContact(String newContactPhone ) throws RemoteException;
    void notifyNewMember(GroupDto groupDto,String newMemberPhone) throws RemoteException;
    void notifyYouAddedToGroup(GroupDto groupDto) throws RemoteException;   
    void tellMessageFromGroup(String message, int groupId, String fromPhone) throws RemoteException;
    void tellFile(String file,byte[] data,String fromPhone) throws RemoteException;

}
