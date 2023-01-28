package gov.iti.link.presentation.controllers;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.UserService;
import gov.iti.link.business.services.UserServiceImp;
import gov.iti.link.presentation.Validations.RegisterValidation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Popup;

public class RegisterController implements Initializable {

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
    private String[] gender = { "Female", "Male" };
    boolean userValid = true;

    @FXML
    private void onRegister() {
        String check = validUser();
        if (userValid) {
            UserDTO user = new UserDTO();
            setUserData(user);
            UserService userService = new UserServiceImp();
            userService.save(user);
            System.out.println("register");

        } else {
            Popup popup = new Popup();
            popup.getContent().add(new Label(check));
            popup.show(StageManager.getInstance().getCurrentStage());
        }
    }

//TODO chose pic and popup design and country

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        genderComboBox.getItems().addAll(gender);
        Image image = new Image(getClass().getResourceAsStream("/images/LinkIn.jpeg"));
        circlePic.setFill(new ImagePattern(image));
    }

    public String validUser() {
        String errMsg = "";
        userValid = true;
        if (!RegisterValidation.validName(txtDisplayName.getText())) {
            System.out.println("accept alphabets and only space character between 2 and 30 characters");
            errMsg += "accept alphabets and only space character between 2 and 30 characters \n";
            userValid = false;
        }
        if (!RegisterValidation.validEmail(txtEmail.getText())) {
            System.out.println("please Enter valid Email ");
            errMsg += "please Email like 'user@domain.com' \n";
            userValid = false;
        }
        if (!RegisterValidation.validPassword(txtPassword.getText(), txtConfirmPassword.getText())) {
            System.out.println("please Enter valid pass ");
            errMsg += "please Enter valid password only numbers between 6 and 10 \n";
            userValid = false;
        }
        if (!RegisterValidation.validPhone(txtPhone.getText())) {
            System.out.println("please Enter valid phone ");
            errMsg += "please Enter valid phine only numbers between 6 and 10\n ";
            userValid = false;
        }
        if (!RegisterValidation.validBio(txtBio.getText())) {
            System.out.println("please Enter bio ");
            errMsg += "please Enter bio \n";
            userValid = false;
        }
        if (!RegisterValidation.validDate(convertLocalDatetoSqlDate(dateOfBirth.getValue()))) {
            System.out.println("please Enter date ");
            errMsg += "please Enter date \n";
            userValid = false;
        }
        if (!RegisterValidation.validGender(genderComboBox.getValue())) {
            System.out.println("please Enter gender ");
            errMsg += "please Enter gender \n";
            userValid = false;
        }

        return errMsg;
    }

    void setUserData(UserDTO user) {
        user.setPhone(txtPhone.getText());
        user.setName(txtDisplayName.getText());
        user.setBio(txtBio.getText());
        user.setCountry(txtBio.getText());    
        user.setDate(convertLocalDatetoSqlDate(dateOfBirth.getValue()));
        user.setEmail(txtEmail.getText());
        user.setGender((String) genderComboBox.getValue());
        user.setPassword(txtPassword.getText());
        user.setPicture(txtDisplayName.getText());
    }

    Date convertLocalDatetoSqlDate(LocalDate localdate){
        if(localdate==null){
            return null;
        }
        Date date = Date.valueOf(localdate);
        return date;
    }

}
