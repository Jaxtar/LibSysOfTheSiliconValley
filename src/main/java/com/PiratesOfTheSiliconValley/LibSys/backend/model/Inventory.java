package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "inventory")
public class Inventory {

    public enum Condition{
        PERFEKT{ public String toString(){return "perfekt";}},
        BRA{ public String toString(){return "bra";}},
        SLITEN{ public String toString(){return "sliten";}},
        TRASIG{ public String toString(){return "trasig";}}
    }

    public enum Status{
        INNE{ public String toString(){return "inne";}},
        UTLÅNAD{ public String toString(){return "utlånad";}},
        RESERVERAD{ public String toString(){return "reserverad";}},
        STULEN{ public String toString(){return "stulen";}},
        FÖRSVUNNEN{ public String toString(){return "försvunnen";}}
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer bookID;

    @NotNull
    @NotEmpty
    private String isbn;

    private String classification;

    @NotNull
    @NotEmpty
    private Inventory.Condition condition;

    @NotNull
    @NotEmpty
    private Inventory.Status status;

    @NotNull
    @NotEmpty
    private Date date_added;

    public Inventory(Integer bookID, String isbn, String classification, Inventory.Condition condition,
                     Inventory.Status status, Date date_added){
        this.bookID = bookID;
        this.isbn = isbn;
        this.classification = classification;
        this.condition = condition;
        this.status = status;
        this.date_added = date_added;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate_added() {
        return date_added;
    }

    public void setDate_added(Date date_added) {
        this.date_added = date_added;
    }
}
