package gov.iti.link.business.DTOs;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {
    private String phone;
    private String name;
    private String email;
    private byte[] picture;
    private String gender;
    private String country;
    private Date date;
    private String password;
    private String bio;
    private List<InvitationDTO> invitations;

    public UserDTO(){
        invitations = new ArrayList<InvitationDTO>();
    }
    
    public String getPhone() {
        return phone;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public byte[] getPicture() {
        return picture;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public Date getDate() {

        return date;
    }

    public String getBio() {
        return bio;
    }

    public String getPassword() {
        return password;
    }

    public List<InvitationDTO> getInvitations() {
        return invitations;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setInvitations(List<InvitationDTO> invitations) {
        this.invitations = invitations;
    }

}
