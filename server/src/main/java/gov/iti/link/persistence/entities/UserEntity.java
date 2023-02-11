package gov.iti.link.persistence.entities;

import java.sql.Date;

public class UserEntity {
    private String phone;
    private String name;
    private String email;
    private byte[] picture;
    private String gender;
    private String country;
    private Date date;
    private String bio;
    private String password;

    public String getPassword() {
        return password;
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

    public UserEntity() {
    }

    public UserEntity(String phone, String name, String email, byte[] picture, String gender, String country, Date date,
            String bio, String password) {
        this.phone = phone;
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.gender = gender;
        this.country = country;
        this.date = date;
        this.bio = bio;
        this.password = password;
    }

}
