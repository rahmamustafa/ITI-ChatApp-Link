package gov.iti.link.persistence.entities;

public class GroupEntity {
    int groupId;
    String groupName;

    
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
}
