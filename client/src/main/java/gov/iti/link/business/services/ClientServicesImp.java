package gov.iti.link.business.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.presentation.controllers.ChatController;
import javafx.application.Platform;

public class ClientServicesImp extends UnicastRemoteObject implements ClientServices {

    ChatController chatController;
    private ServiceManager serviceManager;
    private UserService userService;

    public ClientServicesImp(ChatController chatController) throws RemoteException {
        super();
        this.chatController = chatController;
        serviceManager = ServiceManager.getInstance();
        userService = serviceManager.getUserService();

    }

    @Override
    public void notifyContactStatus(UserDTO userDTO, boolean isActive) throws RemoteException {
        System.out.println(userDTO.getPhone());
        ContactDto contactDto = new ContactDto(userDTO.getPhone(), userDTO.getPicture(), isActive, userDTO.getName());

        Platform.runLater(() -> chatController.changeOnFriendState(contactDto));

    }

    @Override
    public void tellMessage(String message, String fromPhone) throws RemoteException {
        System.out.println("we get " + message + " from " + fromPhone);

        Platform.runLater(() -> {
            try {
                chatController.recieveMessage(message, userService.findByPhone(fromPhone));
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }

    @Override
    public void notifyInvitation(InvitationDTO invitationDTO) {
        System.out.println(invitationDTO.getToPhone() + " recieved an invite from " + invitationDTO.getFromPhone());
        StateManager.getInstance().getUser().getInvitations().add(invitationDTO);
        Platform.runLater(() -> InvitationsState.addInvitation(invitationDTO));

    }

    @Override
    public UserDTO getUserDTO() throws RemoteException {
        return StateManager.getInstance().getUser();

    }

}
