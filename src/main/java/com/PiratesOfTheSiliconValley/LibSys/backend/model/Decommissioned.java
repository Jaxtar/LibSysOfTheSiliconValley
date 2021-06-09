package com.PiratesOfTheSiliconValley.LibSys.backend.model;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "decommissioned")
public class Decommissioned {

    @Id
    private Integer bookID;

    @NotNull
    @NotEmpty
    private String book_isbn;

    @NotNull
    private String title;

    private String classification;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Inventory.Condition book_condition;

    @NotNull
    private LocalDateTime date_added;

    @NotNull
    private LocalDateTime date_removed;

    @NotNull
    private String reason;


    //Getters and setters
    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public String getBook_isbn() {
        return book_isbn;
    }

    public void setBook_isbn(String book_isbn) {
        this.book_isbn = book_isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Inventory.Condition getBook_condition() {
        return book_condition;
    }

    public void setBook_condition(Inventory.Condition book_condition) {
        this.book_condition = book_condition;
    }

    public LocalDateTime getDate_added() {
        return date_added;
    }

    public void setDate_added(LocalDateTime date_added) {
        this.date_added = date_added;
    }

    public LocalDateTime getDate_removed() {
        return date_removed;
    }

    public void setDate_removed(LocalDateTime date_removed) {
        this.date_removed = date_removed;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public Integer getBookID() {
        return bookID;
    }
}
