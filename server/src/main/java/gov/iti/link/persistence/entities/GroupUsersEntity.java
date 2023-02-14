package gov.iti.link.persistence.entities;

public class GroupUsersEntity {
    int id;
    String phone;

    
    public GroupUsersEntity() {
    }

    public GroupUsersEntity(int id, String phone) {
        this.id = id;
        this.phone = phone;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getId() {
        return id;
    }
    
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
}
