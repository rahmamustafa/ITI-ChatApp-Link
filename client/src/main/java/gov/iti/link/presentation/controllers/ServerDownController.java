package gov.iti.link.presentation.controllers;
import gov.iti.link.business.services.StageManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServerDownController {

    @FXML
    private Button Okbtn;

    @FXML
    void OkbtnAction(ActionEvent event) {
        StageManager.getInstance().switchToLogin();
    }
    
}
