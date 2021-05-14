package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer staffID;

    public Staff() {
    }

    public enum Occupation {
        chef, bibliotekarie
    }

    @NotNull
    @NotEmpty
    private String firstname = "";

    @NotNull
    @NotEmpty
    private String lastname = "";

    @NotNull
    @NotEmpty
    private String username = "";

    @NotNull
    @NotEmpty
    private String password = "";

    @NotNull
    @NotEmpty
    private String email = "";

    @Enumerated(EnumType.STRING)
    @NotNull
    private Staff.Occupation occupation;

    public Staff (Integer staffID, String firstname, String lastname, String username,
                  String password, Staff.Occupation occupation){

        this.staffID = staffID;
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.password = password;
        this.occupation = occupation;
    }

    public Integer getStaffID() {
        return staffID;
    }

    public void setStaffID(Integer staffID) {
        this.staffID = staffID;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
}
