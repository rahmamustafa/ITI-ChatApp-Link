package gov.iti.link.presentation.controllers;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.services.ServiceManager;
import gov.iti.link.business.services.UserService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class InviteController implements Initializable {

    private InvitationDTO invite ; 
    private InviteListController inviteListController;
    @FXML
    private Label labelPhone;

    @FXML
    private Button btnAccept;

    @FXML
    private Button btnReject;

    ObservableList<Node> parentInvites;
    Node parent;

    @FXML
    void onAccept(ActionEvent event) {
        System.out.println(invite.getId());
        try {
            ServiceManager.getInstance().getUserService().acceptInvite(invite);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        deleteInvite();
        
        
    }

    @FXML
    void onReject(ActionEvent event) {
        System.out.println(invite.getId());
        try {
            ServiceManager.getInstance().getUserService().rejectInvite(invite);
        } catch (RemoteException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        deleteInvite();
    }

    private void deleteInvite(){
        System.out.println("delete");
        // inviteListController.deleteInvite(this);

        parentInvites.remove(parent);
    }



    public InviteController(){
        invite = new InvitationDTO();
    }

    public void setInvitationDTO(InvitationDTO invite){
        this.invite = invite ;
        labelPhone.setText(invite.getFromPhone());
    }

    public void setInviteListController(InviteListController controller){
        this.inviteListController = controller;
    }

    public void setParentObservableList(ObservableList<Node> parentInvites){
        this.parentInvites = parentInvites;
    }

    public void setParentPane(Node parent){
        this.parent = parent;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        
    }

    
}
