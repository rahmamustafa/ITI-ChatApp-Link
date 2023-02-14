package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import gov.iti.link.business.DTOs.GroupDto;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.UserService;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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

public class LabelGroupController {

    @FXML
    private Circle circlePic;

    @FXML
    private ImageView imgAddMember;

    @FXML
    private Label lablGroupNumber;

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

    public void setGroupSize(int number) {
        lablGroupNumber.setText(Integer.toString(number));
    }

    public String getName() {
        return (lblGroupName.getText());

    }

    public String getGroupSize() {
        return lablGroupNumber.getText();

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
        setGroupSize(groupDto.getAllMembers().size());
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
}
