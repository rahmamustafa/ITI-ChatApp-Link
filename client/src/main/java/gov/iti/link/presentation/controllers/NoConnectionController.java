package gov.iti.link.presentation.controllers;

import gov.iti.link.business.services.ServiceManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class NoConnectionController {
    @FXML
    private Button btnReconnect;

    @FXML
    void onReconnect(ActionEvent event) {
        ServiceManager.getInstance().connectToServer();
    }
}
