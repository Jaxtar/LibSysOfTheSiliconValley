package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userID;

    public Integer getUserID() {
        return userID;
    }

    @NotNull
    @NotEmpty
    private String username;

    private String passwordSalt;
    private String passwordHash;

    @NotNull
    @Column(columnDefinition = "ENUM('USER', 'ADMIN')")
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @NotEmpty
    private String personal_id_number = "";

    @NotNull
    @NotEmpty
    private String firstname = "";

    @NotNull
    @NotEmpty
    private String lastname = "";

    private String phone;

    public User() {
    }

    /*
    public User(String username, String password, Role role) {
        this.username = username;
        this.passwordSalt = RandomStringUtils.random(32);
        this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);

        //this.passwordHash = DigestUtils.sha1Hex(password);
        this.role = role;
    }
     */

    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
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
}