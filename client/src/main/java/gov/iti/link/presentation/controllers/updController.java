package gov.iti.link.presentation.controllers;

import java.rmi.RemoteException;

// import com.google.protobuf.Service;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.UserService;
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

    private UserService userService;
    private ServiceManager serviceManager; 

    

    public updController() {
        serviceManager = ServiceManager.getInstance();
        userService = serviceManager.getUserService();
    }

    @FXML
    void onChooseProfile(ActionEvent event) {

    }

    @FXML
    void OnUpdate(ActionEvent event) {
        
        UserDTO user = new UserDTO();
        setUserData(user);

        try {
            userService.updateUser(user);
            System.out.println("User updated");
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    void AddUserDataToFields(UserDTO user){
        //userService.findByPhone();
        txtDisplayName.setText(user.getName());
        txtBio.setText(user.getBio());
    
        txtEmail.setText(user.getEmail());
        
    }


    void setUserData(UserDTO user) {
        //user.setPhone(txtPhone.getText());
        user.setName(txtDisplayName.getText());
        user.setBio(txtBio.getText());
        //user.setCountry(txtBio.getText());
        //user.setDate(convertLocalDatetoSqlDate(dateOfBirth.getValue()));
        user.setEmail(txtEmail.getText());
        user.setGender((String) genderComboBox.getValue());
        //user.setPassword(txtPassword.getText());
    }

    String getName(){
        return txtDisplayName.getText();
    }

    String getEmail(){
        return txtEmail.getText();
    }
    String getBio(){
        return txtBio.getText();
    }


}
