package gov.iti.link.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.UserService;
import gov.iti.link.business.services.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class LoginController implements Initializable{

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
    void onLogin(ActionEvent event) {
        UserDTO user = new UserDTO();
        UserService userService = new UserServiceImp();
        user = userService.findByPhone(txtPhone.getText());
        if( user!= null && user.getPassword().equals(txtPassword.getText())){
            System.out.println("loggged");
           //StageManager.getInstance().loadView("home");
        }
        else{
            lblErr.setVisible(true);
        }

    }

    @FXML
    void onSgnUp(ActionEvent event) {
        StageManager.getInstance().loadView("register");

    }

    void setUserData(UserDTO user){
        user.setPhone(txtPhone.getText());
        user.setPassword(txtPassword.getText());
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        
    }

}
