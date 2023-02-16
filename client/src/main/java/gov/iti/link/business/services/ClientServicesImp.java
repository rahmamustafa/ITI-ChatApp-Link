package gov.iti.link.business.services;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;

import org.controlsfx.control.Notifications;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.presentation.controllers.ChatController;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    public void tellAnnouce(String announcement) throws RemoteException {
        System.out.println("we get " + announcement + " from Sever");

        Platform.runLater(() -> {

            chatController.recieveAnnounc(announcement);

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

    @Override
    public void notifyNewContact(String newContactPhone) throws RemoteException {
        Platform.runLater(() -> chatController.addNewContact(newContactPhone));
        System.out.println("new Contact added");

    }

    @Override
    public void notifyNewMember(GroupDto groupDto, String newMemberPhone) throws RemoteException {
        System.out.println(groupDto.getGroupId() + " " + groupDto.getGroupName() + groupDto.getAllMembers().size());
        Platform.runLater(() -> chatController.changeOnGroupState(groupDto, newMemberPhone));

    }

    @Override
    public void notifyYouAddedToGroup(GroupDto groupDto) throws RemoteException {
        Platform.runLater(() -> chatController.addNewGroup(groupDto));

    }

    @Override
    public void tellMessageFromGroup(String message, int groupId, String fromPhone) throws RemoteException {
        Platform.runLater(() -> {
            try {
                chatController.recieveMessageFromGroup(message, groupId, userService.findByPhone(fromPhone));
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }

    @Override
    public void tellFileFromGroup(String file, int groupId, byte[] data, String fromPhone) throws RemoteException {
        System.out.println("we get " + file + " from " + fromPhone);

        Platform.runLater(() -> {
            try {
                chatController.recieveFileFromGroup(file, groupId, data, userService.findByPhone(fromPhone));
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }

    @Override
    public void tellFile(String file, byte[] data, String fromPhone) throws RemoteException {
        System.out.println("we get " + file + " from " + fromPhone);

        Platform.runLater(() -> {
            try {
                chatController.recieveFile(file, data, userService.findByPhone(fromPhone));
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

    }

    @Override
    public void notify(String notification) throws RemoteException {
        Image image = new Image(getClass().getResource("/images/icons/bell.png").toString());
        System.out.println(notification);
        Platform.runLater(() -> {
            Notifications.create().title("New Notification!")
                    .text(notification).graphic(new ImageView(image)).darkStyle().position(Pos.BOTTOM_RIGHT)
                    .owner(StageManager.getInstance().getCurrentStage()).show();
        });

    }

    @Override
    public void notifyMytStatus(UserDTO userDTO, String status) throws RemoteException {
        // TODO Auto-generated method stub
        ContactDto contactDto = new ContactDto(userDTO.getPhone(), userDTO.getPicture(), true, userDTO.getName());

        Platform.runLater(() -> chatController.changeMyFriendState(contactDto, status));
    }

    @Override
    public void disconnect() {
        System.out.println("disconnecting");
        Platform.runLater(() -> StageManager.getInstance().switchToNoConnection());
        StageManager.getInstance().deleteView("home");

    }

}
