package gov.iti.link.presentation.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;

import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserService;
import gov.iti.link.presentation.Validations.RegisterValidation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;

public class CreateGroupController {

  
    @FXML
    private Button BtnChooseProfile;

    @FXML
    private Button btnCreateGroup;

    @FXML
    private Label lblImageUrl;

    @FXML
    private TextField tfGroupName;

    private byte[] groupImg;
    

    UserService userService = ServiceManager.getInstance().getUserService() ;;
    UserDTO userDTO;
    ChatController chatController;

    public CreateGroupController(){
        try {
            groupImg =  Files.readAllBytes(Paths.get(getClass().getResource("/images/group.png").toURI()));
        } catch (IOException | URISyntaxException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public void setChatController(ChatController chatController) {
        this.chatController = chatController;
    }
    
    @FXML
    void onCreateGroup(){
    if(!tfGroupName.getText().isBlank())
       try {
        String groupName = tfGroupName.getText();
        GroupDto groupDto = userService.createGroup(new GroupDto(groupName,StateManager.getInstance().getUser().getPhone(),groupImg));
        System.out.println(groupDto);
        System.out.println("group created");
        userService.addMemberToGroup(groupDto,StateManager.getInstance().getUser().getPhone());
    } catch (RemoteException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }

    @FXML
    private void onChooseProfile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif",
                "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(StageManager.getInstance().getCurrentStage());

        if (file != null) {
            try {
                groupImg = Files.readAllBytes(Paths.get(file.toURI()));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String userPictureUrl = file.toURI().toString();
            lblImageUrl.setText(userPictureUrl);

        }
        
    
    }



}
