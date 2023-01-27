package gov.iti.link.presentation.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.UserService;
import gov.iti.link.business.services.UserServiceImp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class RegisterController implements Initializable {
    @FXML
    private TextField tfUsername;

    @FXML
    private Button btnGo;
    @FXML
    private Button btnLogin;
    @FXML
    private Button btnRegister;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private ComboBox genderComboBox;
    @FXML
    private TextField txtBio;
    @FXML
    private TextField txtConfirmPassword;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtDisplayName;
    @FXML
    private TextField txtPhone;
    @FXML
    private Circle circlePic;
    private String[] gender = {"Female" , "Male"};


@FXML
private void onRegister(){
    System.out.println("register");
    UserDTO user = new UserDTO();
    user.setPhone(txtPhone.getText());
    user.setName("zaki");
    UserService userService = new UserServiceImp();
    userService.save(user);
}











@Override
public void initialize(URL arg0, ResourceBundle arg1) {
    genderComboBox.getItems().addAll(gender);
    Image image = new Image(getClass().getResourceAsStream("/images/LinkIn.jpeg"));
    circlePic.setFill(new ImagePattern(image));
}




}
