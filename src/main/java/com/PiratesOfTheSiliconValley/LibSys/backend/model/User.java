package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userID;

    @NotNull
    @NotEmpty
    private String password = "";

    @NotNull
    @NotEmpty
    private String personal_id_number = "";

    @NotNull
    @NotEmpty
    private String firstname = "";

    @NotNull
    @NotEmpty
    private String lastname = "";

    private String streetadress = "";

    private String postalcode = "";

    private String city;

    @NotNull
    @NotEmpty
    private String email = "";

    private String phone;

    public User(Integer userID, String password,
                String personal_id_number, String firstname,
                String lastname, String streetadress,
                String postalcode, String city,
                String email, String phone) {
        this.userID = userID;
        this.password = password;
        this.personal_id_number = personal_id_number;
        this.firstname = firstname;
        this.lastname = lastname;
        this.streetadress = streetadress;
        this.postalcode = postalcode;
        this.city = city;
        this.email = email;
        this.phone = phone;
    }

    public User(){

    }

    public Integer getUserID() {
        return userID;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPersonal_id_number() {
        return personal_id_number;
    }

    public void setPersonal_id_number(String personal_id_number) {
        this.personal_id_number = personal_id_number;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getStreetadress() {
        return streetadress;
    }

    public void setStreetadress(String streetadress) {
        this.streetadress = streetadress;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User " + userID.toString() + ": " + firstname + " " + lastname;
    }

}

