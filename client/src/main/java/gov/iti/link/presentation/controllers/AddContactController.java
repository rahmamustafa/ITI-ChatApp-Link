package gov.iti.link.presentation.controllers;

import java.rmi.RemoteException;

import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.DTOs.UserDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.StateManager;
import gov.iti.link.business.services.UserService;
import gov.iti.link.presentation.Validations.RegisterValidation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class AddContactController {

    @FXML
    private Button buttonAddContact;

    @FXML
    private TextField textfieldPhone;

    UserService userService ;
    UserDTO userDTO;

    public AddContactController(){
        userService =  ServiceManager.getInstance().getUserService() ;
        userDTO = StateManager.getInstance().getUser();
    }

    @FXML
    void onAddContact(){
        String phoneNumber = textfieldPhone.getText();
       
        if (RegisterValidation.validPhone(phoneNumber)) {
            try {
                InvitationDTO invitationDTO  = userService.sendInvite(userDTO.getPhone(), phoneNumber) ;
                System.out.println("result= " + invitationDTO);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
           
        }
        else {
            System.out.println("enter a valid phone");
        }
    }



}
