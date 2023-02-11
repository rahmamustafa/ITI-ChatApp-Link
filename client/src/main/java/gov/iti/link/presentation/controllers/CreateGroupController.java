package gov.iti.link.presentation.controllers;

import java.rmi.RemoteException;

import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserService;
import gov.iti.link.presentation.Validations.RegisterValidation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class CreateGroupController {

    @FXML
    private Button btnCreateGroup;

    @FXML
    private TextField tfGroupName;

    UserService userService = ServiceManager.getInstance().getUserService() ;;
    UserDTO userDTO;
    ChatController chatController;

    public CreateGroupController(){

    }
    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }
    
    @FXML
    void onCreateGroup(){
    if(!tfGroupName.getText().isBlank())
       try {
        String groupName = tfGroupName.getText();
        GroupDto groupDto = userService.createGroup(groupName);
        System.out.println(groupDto);
        System.out.println("group created");
        chatController.addGroupinListView(groupDto);
        userService.addMemberToGroup(groupDto,StateManager.getInstance().getUser().getPhone());
    } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }



}
