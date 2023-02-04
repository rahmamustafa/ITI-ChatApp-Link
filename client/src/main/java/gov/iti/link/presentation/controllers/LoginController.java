package gov.iti.link.presentation.controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.ModelManager;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.UserService;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.presentation.Validations.RegisterValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LoginController implements Initializable {

    @FXML
    private Button btnLogin;

    @FXML
    private Button btnRegister;

    @FXML
    private ImageView imageView;

    @FXML
    private Label lblErr;

    @FXML
    private Label lblLeft;

    @FXML
    private AnchorPane leftPane;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPhone;

    private ServiceManager serviceManager;
    private UserService userService;
    private StateManager stateManager;
    static UserDTO user;

    public LoginController() {
        serviceManager = ServiceManager.getInstance();
        userService = serviceManager.getUserService();
        stateManager = StateManager.getInstance();
    }

    @FXML
    void onLogin(ActionEvent event) {
        user = new UserDTO();
        if (RegisterValidation.validPassword(txtPassword.getText())
                && RegisterValidation.validPhone(txtPhone.getText())) {

            try {
                user = userService.findByPhone(txtPhone.getText());
            } catch (RemoteException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (user != null && user.getPassword().equals(txtPassword.getText())) {
                System.out.println("loggged");
                lblErr.setVisible(false);
                stateManager.setUser(user);
                StageManager.getInstance().switchToHome();
                // StageManager.getInstance().loadView("home");
            } else {
                lblErr.setVisible(true);
            }
        } else {
            lblErr.setVisible(true);
        }

    }

    @FXML
    void onSignUp() {

        StageManager.getInstance().loadView("register");

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        btnLogin.disableProperty().bind(txtPhone.textProperty().isEmpty().or(txtPassword.textProperty().isEmpty()));
        ModelManager modelManager = ModelManager.getInstance();
        UserDTO userDTO = modelManager.getUserInstance();
        txtPhone.textProperty().bindBidirectional(userDTO.getPhoneProperty());

    }

}
