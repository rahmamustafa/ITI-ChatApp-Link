package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.StateManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class ProfileController implements Initializable{
   

    @FXML
    private Button btnBack;

    @FXML
    private Circle circleImage;

    @FXML
    private Label cpss;

    @FXML
    private HBox hboxArrow;

    @FXML
    private Pane leftPane;

    @FXML
    private Pane paneContent;

    @FXML
    private Label prf;

    @FXML
    private Label upd;

    @FXML
    private Label usrname;


    UpdateController updateController ;
    UserDTO user;



    @FXML
    void BackHome() {
          StageManager.getInstance().switchToHome();
    }


    @FXML
    private void updclkaction(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UpdateProfile.fxml"));
            
            Parent prof=loader.load();
            updateController = loader.getController();
            usrname.textProperty().bind(updateController.gTextField().textProperty());
            
            paneContent.getChildren().clear();
            paneContent.getChildren().addAll(prof);
           
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    

    void setUserNewImage(byte[] image){
        InputStream imgStream = new ByteArrayInputStream(image);
        Image img = new Image(imgStream);
        // Image img = new Image(image,false);
        circleImage.setFill(new ImagePattern(img));
        circleImage.setEffect(new DropShadow(+25d,0d,+2d,Color.TRANSPARENT));
        
    }

    @FXML
    private void prfclkaction(){
        try {
            Parent prof=FXMLLoader.load(getClass().getResource("/views/ProfileInfo.fxml"));
            paneContent.getChildren().clear();
            paneContent.getChildren().add(prof);
    
        } catch (IOException e) {
            
            e.printStackTrace();
        }
            
    }
    @FXML
    private void chgpassclkaction(){
        try {
            Parent prof=FXMLLoader.load(getClass().getResource("/views/ChangePassword.fxml"));
            paneContent.getChildren().clear();
            paneContent.getChildren().addAll(prof);
    
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    



    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
     try {

        Parent prof=FXMLLoader.load(getClass().getResource("/views/ProfileInfo.fxml"));
        paneContent.getChildren().clear();
        paneContent.getChildren().addAll(prof);
        user = StateManager.getInstance().getUser();
        // if(user.getPicture().isEmpty()){
        //     Image img = new Image(user.getPicture(),false);
        // }
        Image img = new Image(getClass().getResource("/images/avatar.jpg").toString(),false);
        circleImage.setFill(new ImagePattern(img));
        circleImage.setEffect(new DropShadow(+25d,0d,+2d,Color.TRANSPARENT));
        usrname.setText(user.getName());
       


    } catch (IOException e) {
        e.printStackTrace();
    }
        
        
    }
    
}
