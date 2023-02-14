package gov.iti.link.business.DTOs;

import java.io.Serializable;

public class ContactDto implements Serializable{

    String phoneNumber;
    byte[] image;
    boolean isActive;
    String name;

    
    public ContactDto() {
    }
    public ContactDto(UserDTO userDTO) {
        this.image=userDTO.getPicture();
        this.phoneNumber=userDTO.getPhone();
        this.name=userDTO.getName();
        isActive=true;
    }
    
    public ContactDto(String phoneNumber, byte[] image, boolean isActive, String name) {
        this.phoneNumber = phoneNumber;
        this.image = image;
        this.isActive = isActive;
        this.name = name;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public byte[] getImage() {
        return image;
    }
    public void setImage(byte[] image) {
        this.image = image;
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
