package gov.iti.link.business.DTOs;

import java.io.Serializable;
import java.util.Vector;

public class GroupDto implements Serializable{
    int groupId;
    String groupName;
    String adminPhone;
    Vector<String> allMembers  = new Vector<>();
    private byte[] picture;
    
    public String getAdminPhone() {
        return adminPhone;
    }

    public void setAdminPhone(String adminPhone) {
        this.adminPhone = adminPhone;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public GroupDto() {
    }

    public GroupDto(String groupName) {
        this.groupName = groupName;
    }


    public GroupDto(int groupId, String groupName) {
        this.groupId = groupId;
        this.groupName = groupName;
    }

    public GroupDto(int groupId, String groupName, String adminPhone, byte[] picture) {
        this.groupId = groupId;
        this.groupName = groupName;
        this.adminPhone = adminPhone;
        this.allMembers = allMembers;
        this.picture = picture;
    }

    public GroupDto( String groupName, String adminPhone, byte[] picture) {
        this.groupName = groupName;
        this.adminPhone = adminPhone;
        this.allMembers = allMembers;
        this.picture = picture;
    }

    public int getGroupId() {
        return groupId;
    }
    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Vector<String> getAllMembers() {
        return allMembers;
    }

    public void setAllMembers(Vector<String> allMembers) {
        this.allMembers = allMembers;
    }
    public void addMember(String member) {
        this.allMembers.add(member);
    }
 
    @Override
    public String toString() {
        return "GroupDto [groupId=" + groupId + ", groupName=" + groupName + "]";
    }
}
