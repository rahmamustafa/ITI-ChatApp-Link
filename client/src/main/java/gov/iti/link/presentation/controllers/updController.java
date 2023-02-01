package gov.iti.link.presentation.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class updController {

    @FXML
    private Button BtnChooseProfile;

    @FXML
    private Button btnLogin;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private ComboBox<?> genderComboBox;

    @FXML
    private Label lblErrBio;

    @FXML
    private Label lblErrDate;

    @FXML
    private Label lblErrEmail;

    @FXML
    private Label lblErrGender;

    @FXML
    private Label lblErrName;

    @FXML
    private Label lblErrPass;

    @FXML
    private Label lblErrPhone;

    @FXML
    private Label lblImageUrl;

    @FXML
    private TextField txtBio;

    @FXML
    private TextField txtDisplayName;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtPhone;

    @FXML
    void onChooseProfile(ActionEvent event) {

    }

}
