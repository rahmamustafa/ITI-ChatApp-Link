package gov.iti.link.business.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.presentation.controllers.ChatController;
import javafx.application.Platform;

public class ClientServicesImp extends UnicastRemoteObject implements ClientServices{

    ChatController chatController;
    public ClientServicesImp(ChatController chatController) throws RemoteException {
        super();
        this.chatController=chatController;
    }



    @Override
    public void notifyContactStatus(UserDTO contactDto, boolean isActive) throws RemoteException {
        System.out.println(contactDto.getPhone());
        Platform.runLater(()->chatController.changeOnFriendState(contactDto.getPhone(),isActive));
        
    }
    
}
