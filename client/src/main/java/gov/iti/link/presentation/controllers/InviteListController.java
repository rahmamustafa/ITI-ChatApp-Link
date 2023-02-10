package gov.iti.link.presentation.controllers;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.business.services.InvitationsState;
import gov.iti.link.business.services.StateManager;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.VBox;

public class InviteListController implements Initializable {
    
    @FXML
    private VBox vboxInviteList;

    ObservableList<Node> invites; 
    ObservableList<InvitationDTO> invitations;
    

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        Bindings.bindContentBidirectional(invites, vboxInviteList.getChildren());
        System.out.println("invitation-list");
        // List<InvitationDTO> invitations =  StateManager.getInstance().getUser().getInvitations();
        invitations = InvitationsState.getInvitations();
        for(InvitationDTO invite:invitations){
               
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/components/invite.fxml"));
            try {
                Node invitePane = fxmlLoader.load();
                InviteController controller =  fxmlLoader.getController();
                controller.setInvitationDTO(invite);
                controller.setParentObservableList(invites);
                controller.setParentPane(invitePane);
                invites.add(invitePane);
              
                // vboxInviteList.getChildren().add(invitePane);
                
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    public InviteListController(){
        System.out.println("InviteListController");
        invites = FXCollections.observableArrayList();
        
    }

    public void deleteInvite(Object obj){
        vboxInviteList.getChildren().remove(obj);
    }

}
