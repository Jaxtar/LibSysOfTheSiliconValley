package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import org.apache.commons.codec.digest.DigestUtils;
import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User extends AbstractEntity{

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Person person;

    @NotNull
    @NotEmpty
    private String username;

   // private String passwordSalt;
    private String passwordHash;

    @NotNull
    @Column(columnDefinition = "ENUM('USER', 'ADMIN')")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        //this.passwordSalt = RandomStringUtils.random(32);
        //this.passwordHash = DigestUtils.sha1Hex(password + passwordSalt);

        this.passwordHash = DigestUtils.sha1Hex(password);
        this.role = role;
    }


    public boolean checkPassword(String password) {
        return DigestUtils.sha1Hex(password).equals(passwordHash);
       // return DigestUtils.sha1Hex(password + passwordSalt).equals(passwordHash);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /*
    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }
     */

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

}