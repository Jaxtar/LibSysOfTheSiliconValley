package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userID;

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String username;

    private String passwordSalt;
    private String passwordHash;

    @NotNull
    @Column(columnDefinition = "ENUM('ADMIN', 'STAFF', 'USER')")
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String personal_id_number = "";

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String firstname = "";

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String lastname = "";

    private String phone;

    @Email
    private String email = "";

    private Integer card_id;


    //Getters and setters
    public Integer getUserID() {
        return userID;
    }
    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public Integer getCard_id() {
        return card_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String password) {
        this.passwordSalt = RandomStringUtils.random(4);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}