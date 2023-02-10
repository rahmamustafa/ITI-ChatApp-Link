package gov.iti.link.business.services;

import java.util.List;

import gov.iti.link.business.DTOs.InvitationDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class InvitationsState {
    private static ObservableList<InvitationDTO> invitationsList = FXCollections.observableArrayList();

    public static ObservableList<InvitationDTO> getInvitations() {
        return invitationsList;
    }

    public static void addInvitation(InvitationDTO invitation) {
        invitationsList.add(invitation);
    }

    public static void delete(InvitationDTO invitation) {
        invitationsList.remove(invitation);
    }

    public static void setInvitations(List<InvitationDTO> invitations) {
        invitationsList = FXCollections.observableArrayList(invitations);
    }
}
