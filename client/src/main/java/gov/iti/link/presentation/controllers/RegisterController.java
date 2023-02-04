package gov.iti.link.presentation.controllers;

import java.io.File;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.ModelManager;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.UserService;
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
import javafx.stage.FileChooser;
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
    private Label lblImageUrl;
    @FXML
    private Label lblErrName;
    @FXML
    private Label lblErrEmail;
    @FXML
    private Label lblErrPhone;
    @FXML
    private Label lblErrPass;
    @FXML
    private Label lblErrConfirmPass;
    @FXML
    private Label lblErrGender;
    @FXML
    private Label lblErrDate;
    @FXML
    private Label lblErrBio;
    @FXML
    private Circle circlePic;

    private String userPictureUrl = null;
    private String[] gender = { "Female", "Male" };
    boolean userValid = true;


    private ServiceManager serviceManager ;
    private UserService userService;

    public RegisterController(){
        serviceManager = ServiceManager.getInstance();
        userService = serviceManager.getUserService();
    }


    @FXML
    private void onRegister() {
        String check = validUser();
        if (userValid) {
            UserDTO user = new UserDTO();
            setUserData(user);
            // UserService userService = new UserServiceImp();
            System.out.println(userPictureUrl);
            try {
                userService.save(user);
                StageManager.getInstance().switchToLogin();
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            System.out.println("register");

            /*
             * else {
             * Popup popup = new Popup();
             * popup.getContent().add(new Label(check));
             * popup.show(StageManager.getInstance().getCurrentStage());
             * }
             */

            //circlePic.setFill(new ImagePattern(new Image(user.getPicture())));
        }
    }
    @FXML
    void onLogin() {
        StageManager.getInstance().loadView("login");

    }
    @FXML
    private void onChooseProfile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif",
                "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(StageManager.getInstance().getCurrentStage());

        if (file != null) {
            userPictureUrl = file.toURI().toString();
            lblImageUrl.setText(userPictureUrl);

        }
    }

    // TODO chose pic and popup design and country

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        genderComboBox.getItems().addAll(gender);
        Image image = new Image(getClass().getResourceAsStream("/images/LinkIn.jpeg"));
        circlePic.setFill(new ImagePattern(image));
        ModelManager modelManager = ModelManager.getInstance();
        UserDTO userDTO = modelManager.getUserInstance();
        txtPhone.textProperty().bindBidirectional(userDTO.getPhoneProperty());
    }

    public String validUser() {
        String errMsg = "";
        userValid = true;
        if (!RegisterValidation.validName(txtDisplayName.getText())) {
            System.out.println("accept alphabets and only space character between 2 and 30 characters");
            errMsg += "accept alphabets and only space character between 2 and 30 characters \n";
            userValid = false;
            lblErrName.setVisible(true);

        } else
            lblErrName.setVisible(false);

        if (!RegisterValidation.validEmail(txtEmail.getText())) {
            System.out.println("please Enter valid Email ");
            errMsg += "please Email like 'user@domain.com' \n";
            lblErrEmail.setVisible(true);
            userValid = false;
        } else
            lblErrEmail.setVisible(false);

        if (!RegisterValidation.validPassword(txtPassword.getText())) {
            System.out.println("please Enter valid pass ");
            errMsg += "please Enter valid password only numbers between 6 and 10 \n";
            lblErrPass.setVisible(true);
            userValid = false;
        } else
            lblErrPass.setVisible(false);
        if (!RegisterValidation.validConfirmPassword(txtPassword.getText(), txtConfirmPassword.getText())) {
            System.out.println("please Enter valid pass ");
            errMsg += "password doesn't match \n";
            lblErrConfirmPass.setVisible(true);
            userValid = false;
        } else
            lblErrConfirmPass.setVisible(false);

        if (!RegisterValidation.validPhone(txtPhone.getText())) {
            System.out.println("please Enter valid phone ");
            errMsg += "please Enter valid phone number\n ";
            lblErrPhone.setVisible(true);
            userValid = false;
        }
         else if (!uniquePhone(txtPhone.getText())) {
            System.out.println("please Enter valid phone ");
            errMsg += "this number is exist \n ";
            lblErrPhone.setText("this number exists");
            lblErrPhone.setVisible(true);
            userValid = false;
        } 
        else
            lblErrPhone.setVisible(false);

        if (!RegisterValidation.validBio(txtBio.getText())) {
            System.out.println("please Enter bio ");
            errMsg += "please Enter bio \n";
            lblErrBio.setVisible(true);
            userValid = false;
        } else
            lblErrPhone.setVisible(false);

        if (!RegisterValidation.validDate(convertLocalDatetoSqlDate(dateOfBirth.getValue()))) {
            System.out.println("please Enter date ");
            errMsg += "please Enter date \n";
            lblErrDate.setVisible(true);
            userValid = false;
        } else
            lblErrDate.setVisible(false);

        if (!RegisterValidation.validGender(genderComboBox.getValue())) {
            System.out.println("please Enter gender ");
            errMsg += "please Enter gender \n";
            lblErrGender.setVisible(true);
            userValid = false;
        } else
            lblErrGender.setVisible(false);

        if (userPictureUrl == null) {
            System.out.println("please Enter profile ");
            errMsg += "please choose profile \n";
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
        user.setPicture(userPictureUrl);
    }

    Date convertLocalDatetoSqlDate(LocalDate localdate) {
        if (localdate == null) {
            return null;
        }
        Date date = Date.valueOf(localdate);
        return date;
    }

    public boolean uniquePhone(String phone) {
    
        try {
            if(userService.findByPhone(phone)!=null){
                return false;
            }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (phone == null) {
            return false;
        }
        return true;
    }

}
