package gov.iti.link.business.mappers;

import gov.iti.link.business.DTOs.ContactDto;
import gov.iti.link.persistence.entities.ContactEntity;

public class ContactMapper {
    // public ContactEntity dtoToEntity(ContactDto contactDto,String userPhone) {
    //     ContactEntity contactEntity = new ContactEntity();
    //     contactEntity.setFriendPhone(contactDto.getPhoneNumber());
    //     contactEntity.setUserPhone(userPhone);
    //     return contactEntity;
    // }

    public ContactDto entityToDTO(ContactEntity contactEntity) {
        ContactDto contactDto = new ContactDto();
        contactDto.setPhoneNumber(contactEntity.getFriendPhone());
        return contactDto;
    }

    
}
