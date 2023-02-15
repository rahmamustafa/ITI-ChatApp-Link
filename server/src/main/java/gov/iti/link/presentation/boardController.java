package gov.iti.link.presentation;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

public class boardController {

   
    @FXML
        private VBox paneconttent;
    @FXML
    void DataAvilabilityOnAction(MouseEvent event) {

    }

    @FXML
    void MangeServiceOnAction(MouseEvent event) {
        try {
                        Parent prof=FXMLLoader.load(getClass().getResource("/view/ManageService.fxml"));
                        paneconttent.getChildren().clear();
                        paneconttent.getChildren().addAll(prof);
                
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }
                

    }

    @FXML
    void ServerAnnouncementOnAction(MouseEvent event) {
       try {
                        Parent prof=FXMLLoader.load(getClass().getResource("/view/ServerAnnouncement.fxml"));
                        paneconttent.getChildren().clear();
                        paneconttent.getChildren().addAll(prof);
                
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }
    }

    @FXML
    void ServerAvalabilityOnAction(MouseEvent event) {
        try {
                        Parent prof=FXMLLoader.load(getClass().getResource("/view/ServerAvailability.fxml"));
                        paneconttent.getChildren().clear();
                        paneconttent.getChildren().addAll(prof);
                
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }
    }

    @FXML
    void ServerStatisticsOnAction(MouseEvent event) {
        try {
            Parent prof=FXMLLoader.load(getClass().getResource("/view/Statistics.fxml"));
            paneconttent.getChildren().clear();
            paneconttent.getChildren().addAll(prof);
    
        } catch (IOException e) {
            
            e.printStackTrace();
        }
    }

    @FXML
    void UserRegistrationOnAction(MouseEvent event) {
        try {
                        Parent prof=FXMLLoader.load(getClass().getResource("/view/UserRegistration.fxml"));
                        paneconttent.getChildren().clear();
                        paneconttent.getChildren().addAll(prof);
                
                    } catch (IOException e) {
                        
                        e.printStackTrace();
                    }
            
    }

}
