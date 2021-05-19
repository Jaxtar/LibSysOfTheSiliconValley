package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer userID;

    public Integer getUserID() {
        return userID;
    }

    @Override
    public int hashCode() {
        if (userID != null) {
            return userID.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof AbstractEntity)) {
            return false; // null or other class
        }
        AbstractEntity other = (AbstractEntity) obj;

        if (userID != null) {
            return userID.equals(other.userID);
        }
        return super.equals(other);
    }
}