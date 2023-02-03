package gov.iti.link.persistence.DAOs;

import java.util.List;
import java.util.Optional;
import java.util.Vector;

import gov.iti.link.persistence.entities.ContactEntity;
import gov.iti.link.persistence.entities.InvitationEntity;
import gov.iti.link.persistence.entities.UserEntity;

public interface UserDao {
    public UserEntity save(UserEntity user);
    public Optional<UserEntity> findByPhone(String phone);
    public Vector<UserEntity> getAllUsers();
    public int saveInvitation(String fromPhone, String toPhone);
    public int addContact(String userPhone,String friendPhone);
    public Vector<ContactEntity> getAllContacts(String userPhone);
    boolean updateUser(UserEntity user);

    public List<InvitationEntity> getUserInvitations(String userPhone);

    // public ContactEntity addContact(String fromPhone,String toPhone);
}
