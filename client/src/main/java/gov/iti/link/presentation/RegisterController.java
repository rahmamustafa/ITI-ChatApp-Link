package gov.iti.link.presentation;

import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.UserService;
import gov.iti.link.business.services.UserServiceImp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RegisterController {
    @FXML
    private TextField tfPhone;
    @FXML
    private Button btnRegister;

    public void onRegister(ActionEvent e){
        System.out.println("register");
        UserDTO user = new UserDTO();
        user.setPhone(tfPhone.getText());
        user.setName("zaki");
        UserService userService = new UserServiceImp();
        userService.save(user);
    }
}
