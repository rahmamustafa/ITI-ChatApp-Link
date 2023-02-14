package gov.iti.link.presentation.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

// import com.google.protobuf.Service;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StageManager;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserService;
import gov.iti.link.presentation.Validations.RegisterValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

public class UpdateController implements Initializable{

    @FXML
    private Button BtnChooseProfile;

    @FXML
    private Button btnLogin;

    @FXML
    private DatePicker dateOfBirth;

    @FXML
    private ComboBox<String> countryComboBox;

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
    
    UserDTO user;
    boolean userValid = true;
    ProfileController profileController;
    byte [] userImage;
    

    public UpdateController() {
        serviceManager = ServiceManager.getInstance();
        userService = serviceManager.getUserService();        
    }

    public void setProfileController(ProfileController profileController) {
        this.profileController = profileController;
    }

    @FXML
    void onChooseProfile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.png", "*.gif",
                "*.jpeg");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(StageManager.getInstance().getCurrentStage());
        
        if(file == null){
            return;
        }
        
            try {
                userImage = Files.readAllBytes(Paths.get(file.toURI()));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        
        
    }
    

    @FXML
    void OnUpdate(ActionEvent event) {
        
        //UserDTO user = new UserDTO();
        System.out.println("User image before : " + user.getPicture());
        setNewUserData(user);
        if(validUser()){
        try {
                if(userService.updateUser(user) != -1){
                    
                     System.out.println("User updated " + user.getName());
                     Alert alert = new Alert(AlertType.CONFIRMATION , "User has been updated" ,new ButtonType("OK", ButtonBar.ButtonData.OK_DONE));
                     alert.show();
                     StateManager.getInstance().setUser(user);
                     System.out.println("User image after : " + user.getPicture());
                     profileController.setUserNewImage(user.getPicture());
                }
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    }


    


    void AddUserDataToFields(UserDTO user){
        //userService.findByPhone();
        
        
        txtDisplayName.setText(user.getName());
        //usrname.setText(user.getName());
        txtBio.setText(user.getBio());
        
        txtEmail.setText(user.getEmail());
        dateOfBirth.setValue(user.getDate().toLocalDate());
        countryComboBox.setValue( (String) user.getCountry());
        //user.setPicture(userImage);
        //genderComboBox.setValue(user.getGender());
        
    }

    Date convertLocalDatetoSqlDate(LocalDate localdate) {
        if (localdate == null) {
            return null;
        }
        Date date = Date.valueOf(localdate);
        return date;
    }

    void setNewUserData(UserDTO user) {
        
        user.setName(txtDisplayName.getText());
        user.setBio(txtBio.getText());
        user.setCountry(countryComboBox.getValue());
        user.setEmail(txtEmail.getText());
        user.setDate(convertLocalDatetoSqlDate(dateOfBirth.getValue()));
        user.setPicture(userImage);

    }

    public boolean validUser() {
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


        return userValid;
    }
    String getName(){
        return txtDisplayName.getText();
    }
    TextField gTextField() {
        return txtDisplayName;
    }
    String getEmail(){
        return txtEmail.getText();
    }
    String getBio(){
        return txtBio.getText();
    }

    void GetBind(Label label){
        label.textProperty().bind(txtDisplayName.textProperty());
    }


    private void addCountriesComboBox(){
        ObservableList<String> cities = FXCollections.observableArrayList();
        String[] locals = Locale.getISOCountries();
        for (String country : locals) {
            Locale locale = new Locale("", country);
            String [] city = {locale.getDisplayCountry()};
            for (int i = 0; i < city.length; i++) {
                cities.add(locale.getDisplayCountry());
            }
            cities.add(locale.getDisplayCountry());
            
        }
        this.countryComboBox.setItems(cities);
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
        user = StateManager.getInstance().getUser();
        //usrname.textProperty().bind(txtDisplayName.textProperty());
        addCountriesComboBox();
        AddUserDataToFields(user);  
    }


}
