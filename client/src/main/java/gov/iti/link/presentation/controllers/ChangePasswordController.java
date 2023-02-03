package gov.iti.link.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserService;
import gov.iti.link.presentation.Validations.RegisterValidation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

public class ChangePasswordController implements Initializable {

    @FXML
    private Button btnChangePassword;

    @FXML
    private Label label;

    @FXML
    private Label lblErrConfirmPass;

    @FXML
    private Label lblImageUrl;

    @FXML
    private PasswordField txtConfirmPassword;

    @FXML
    private Label txtErrPassRange;

    @FXML
    private PasswordField txtNewPassword;

    @FXML
    private PasswordField txtOldPassword;

    ServiceManager serviceManager;

    UserService userService;

    UserDTO user;

    private boolean userValid = true;

    public ChangePasswordController() {
        serviceManager = ServiceManager.getInstance();
        userService = serviceManager.getUserService();
    }

    @FXML
    void OnChange(ActionEvent event) {
        //user.setPassword(txtPassword.getText());
        SetUserPasswords(user);
        
        if (validatePassword()) {

            try {
                if (userService.updateUser(user) != -1) {

                    System.out.println("User password " + user.getPassword());
                    Alert alert = new Alert(AlertType.CONFIRMATION, "User has been updated",
                            new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
                    alert.show();
                }

            } catch (Exception e) {
                // TODO: handle exception
            }
        }

    }

    void SetUserPasswords(UserDTO user) {
        if (txtNewPassword.getText().equals(txtConfirmPassword.getText()))
            user.setPassword(txtNewPassword.getText());
        else
            lblErrConfirmPass.setVisible(true);
    }

    boolean validatePassword() {
        if (!RegisterValidation.validPassword(txtNewPassword.getText())) {
            System.out.println("please Enter valid pass ");

            txtErrPassRange.setVisible(true);
            userValid = false;
        } else
            txtErrPassRange.setVisible(false);

        return userValid;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        user = StateManager.getInstance().getUser();
        btnChangePassword.disableProperty().bind(txtNewPassword.textProperty().isEmpty().or(txtConfirmPassword.textProperty().isEmpty()));
        

    }

}
