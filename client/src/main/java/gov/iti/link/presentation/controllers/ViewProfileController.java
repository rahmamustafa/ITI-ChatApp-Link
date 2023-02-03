package gov.iti.link.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.link.business.services.StateManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

public class ViewProfileController implements Initializable {
    @FXML
    private Label LabelBio;

    @FXML
    private Label LabelDatatOfBaeth;

    @FXML
    private Label LabelEmail;

    @FXML
    private Label LabelGender;

    @FXML
    private Label LableNam;

    @FXML
    private Label LablePhone;

    @FXML
    private Label UserBio;

    @FXML
    private Label UserDateOfBirth;

    @FXML
    private Label UserEmail;

    @FXML
    private Label UserGender;

    @FXML
    private Label UserName;

    @FXML
    private Label UserPhone;
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        UserName.setText(StateManager.getInstance().getUser().getName());
        UserPhone.setText(StateManager.getInstance().getUser().getPhone());
        UserEmail.setText(StateManager.getInstance().getUser().getEmail());
        UserGender.setText(StateManager.getInstance().getUser().getGender());
        UserBio.setText(StateManager.getInstance().getUser().getBio());
        UserDateOfBirth.setText(StateManager.getInstance().getUser().getDate().toString());
    }
    
}
