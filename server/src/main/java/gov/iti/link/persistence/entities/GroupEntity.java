package gov.iti.link.persistence.entities;

public class GroupEntity {
    int groupId;
    String groupName;
    String adminPhone;
    private byte[] picture;


    
    public GroupEntity() {
    }
    
    public GroupEntity(String groupName) {
        this.groupName = groupName;
    }

    public GroupEntity(int groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public int getGroupId() {
        return groupId;
    }
    
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public GroupEntity(int groupId, String groupName, String adminPhone, byte[] picture) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.adminPhone = adminPhone;
        this.picture = picture;
    }

    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }
}
