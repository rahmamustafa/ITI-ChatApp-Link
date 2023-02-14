package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.UserService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.StageStyle;

public class LabelGroupController implements Initializable {

    @FXML
    private Circle circlePic;

    @FXML
    private ImageView imgAddMember;

    private boolean seenLastMessage=true;
    private int counterLastMessage=0; 

    

    @FXML
    private Label lblunSeenMessages;

    @FXML
    private Label lblGroupName;

    @FXML
    private Text txtLastMessage;

    GroupDto groupDto;

    UserService userService = ServiceManager.getInstance().getUserService();;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML
    void onAddGroupMember(MouseEvent mouseEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/friends-checkList.fxml"));
        DialogPane addDialogPane;
        try {
            addDialogPane = fxmlLoader.load();
            Dialog<Boolean> dialog = new Dialog<>();
            FriendsListController friendsListController = fxmlLoader.getController();
            friendsListController.setGroupDto(groupDto);
            friendsListController.setDialog(dialog);
            makeDialogDraggable(addDialogPane,dialog);
            dialog.setDialogPane(addDialogPane);
            dialog.initStyle(StageStyle.TRANSPARENT);
            dialog.showAndWait();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public LabelGroupController() {

    }

    public LabelGroupController(String groupName) {
        setGroupName(groupName);
    }

    public void setGroupName(String name) {
        lblGroupName.setText(name);

    }

    public void setLastMessage(String message) {
        txtLastMessage.setText(message);
    }

    

    public String getName() {
        return (lblGroupName.getText());

    }

    public void setImage(byte[] image){
        InputStream imgStream = new ByteArrayInputStream(image);
        Image img = new Image(imgStream);
        // Image image = new Image(imageUrl);
        circlePic.setFill(new ImagePattern(img));
        // System.out.println("ads");
    }


   
    public void setGroupDto(GroupDto groupDto){
        this.groupDto=groupDto;
        setGroupName(groupDto.getGroupName());
        //setGroupSize(groupDto.getAllMembers().size());
        setImage(groupDto.getPicture());
        
    }

    public void setAddMemberDisable() {
        imgAddMember.setVisible(false);
    }

    private void makeDialogDraggable(Pane pane, Dialog dialog){
        pane.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });

     
        
        pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                dialog.setX(event.getScreenX() - xOffset);
                dialog.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public void setSeenLastMessage(boolean seenLastMessage) {
        this.seenLastMessage = seenLastMessage;
        if(seenLastMessage){
           lblunSeenMessages.setVisible(false);
            counterLastMessage=0;
        }
        else{
            lblunSeenMessages.setVisible(true);
            counterLastMessage++;
            lblunSeenMessages.setText(Integer.toString(counterLastMessage));
        }
	}

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        lblunSeenMessages.setVisible(false);
        
    }
}
