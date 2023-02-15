package gov.iti.link.presentation.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ClientServices;
import gov.iti.link.business.services.ClientServicesImp;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.UserService;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserAuth;
import gov.iti.link.presentation.Validations.RegisterValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
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

    @FXML
    private CheckBox cbStayLoggedIn;

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
            String hashedPassword = serviceManager.hashingPassword(txtPassword.getText().toString());
            try {

                user = userService.findByPhone(txtPhone.getText());
            } catch (RemoteException e) {
                StageManager.getInstance().switchToServerDown();
                //StageManager.getInstance().switchToServerDown();
                // TODO Auto-generated catch block
                e.printStackTrace();
                
            }
            if (user != null && user.getPassword().equals(hashedPassword)) {
                System.out.println("loggged");
                lblErr.setVisible(false);
                user.setPassword(hashedPassword);
                stateManager.setUser(user);
                // Remember User
                if (cbStayLoggedIn.isSelected())
                    UserAuth.rememberUser();

                StageManager.getInstance().switchToHome();
                // StageManager.getInstance().loadView("home");
            } else {
                // System.out.println(user.getPassword());
                // System.out.println(serviceManager.hashingPassword(txtPassword.getText().toString()));
                lblErr.setVisible(true);
            }
        }
         else {
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

    }

}
