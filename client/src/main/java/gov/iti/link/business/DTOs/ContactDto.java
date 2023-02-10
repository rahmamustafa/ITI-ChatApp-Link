package gov.iti.link.business.DTOs;

import java.io.Serializable;

public class ContactDto implements Serializable{

    String phoneNumber;
    String imageUrl;
    boolean isActive;
    String name;

    
    public ContactDto() {
    }
    public ContactDto(UserDTO userDTO) {
        this.imageUrl=userDTO.getPicture();
        this.phoneNumber=userDTO.getPhone();
        this.name=userDTO.getName();
        isActive=true;
    }
    
    public ContactDto(String phoneNumber, String imageUrl, boolean isActive, String name) {
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
        this.isActive = isActive;
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public boolean isActive() {
        return isActive;
    }
    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
     
}
