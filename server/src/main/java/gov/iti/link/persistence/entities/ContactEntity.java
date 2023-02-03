package gov.iti.link.persistence.entities;

public class ContactEntity {
    String userPhone;
    String friendPhone;
    int id;

    
    public ContactEntity() {
    }
    public ContactEntity(String userPhone, String friendPhone) {
        this.userPhone = userPhone;
        this.friendPhone = friendPhone;
    }
    public String getUserPhone() {
        return userPhone;
    }
    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
    public String getFriendPhone() {
        return friendPhone;
    }
    public void setFriendPhone(String friendPhone) {
        this.friendPhone = friendPhone;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    
}
