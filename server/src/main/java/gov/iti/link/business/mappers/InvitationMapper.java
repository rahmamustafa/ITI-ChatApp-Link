package gov.iti.link.business.mappers;

import gov.iti.link.business.DTOs.InvitationDTO;
import gov.iti.link.persistence.entities.InvitationEntity;

public class InvitationMapper {
    public static InvitationEntity dtoToEntity(InvitationDTO invitation) {
        InvitationEntity invitationEntity = new InvitationEntity(invitation.getId(), invitation.getFromPhone(),
                invitation.getToPhone(), invitation.getDate());

        return invitationEntity;
    }

    public static InvitationDTO entityToDTO(InvitationEntity invitation) {
        InvitationDTO invitationDTO = new InvitationDTO(invitation.getId(), invitation.getFromPhone(),
                invitation.getToPhone(), invitation.getDate());
        return invitationDTO;

    }
}
