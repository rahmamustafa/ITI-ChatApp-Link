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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddContactController {

    @FXML
    private Button buttonAddContact;

    @FXML
    private TextField textfieldPhone;

    @FXML
    private Label labelMessage;

    UserService userService;
    UserDTO userDTO;
    Dialog<Boolean> dialog;

    public AddContactController() {
        userService = ServiceManager.getInstance().getUserService();
        userDTO = StateManager.getInstance().getUser();
    }

    @FXML
    void onAddContact() {
        String phoneNumber = textfieldPhone.getText();

        if (RegisterValidation.validPhone(phoneNumber)) {
            if (phoneNumber == StateManager.getInstance().getUser().getPhone()) {
                showErrorMessage("You can't Add yourself");
            }
            try {
                InvitationDTO invitationDTO = userService.sendInvite(userDTO.getPhone(), phoneNumber);
                System.out.println("result= " + invitationDTO);
                if (invitationDTO != null) {
                    showSuccessMessage("Invitation Sent");
                } else {
                    showErrorMessage("Invitation couldn't be sent");
                }

            } catch (RemoteException e) {
                e.printStackTrace();
            }

        } else {
            System.out.println("enter a valid phone");
            showErrorMessage("Enter a valid phone");
        }

        textfieldPhone.clear();
    }

    @FXML
    void onClose(){
        this.dialog.setResult(Boolean.TRUE);
        dialog.close();
    }

    private void showErrorMessage(String msg) {
        labelMessage.setText(msg);
        labelMessage.setVisible(true);
        labelMessage.getStyleClass().setAll("error-label");
    }

    private void showSuccessMessage(String msg) {
        labelMessage.setText(msg);
        labelMessage.setVisible(true);
        labelMessage.getStyleClass().setAll("sucess-label");

    }
    
    public void setDialog(Dialog<Boolean> dialog){
        this.dialog = dialog ;
    }



}
