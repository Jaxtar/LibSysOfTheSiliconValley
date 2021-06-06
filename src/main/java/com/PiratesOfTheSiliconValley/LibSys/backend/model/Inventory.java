package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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

    public Integer getBookID() {
        return bookID;
    }

    @NotNull
    private String isbn;

    @NotNull
    private String title;

    private String classification;

    @Enumerated(EnumType.STRING)
    @NotNull()
    private Inventory.Condition book_condition;

    @Enumerated(EnumType.STRING)
    @NotNull()
    private Inventory.Status status;

    @NotNull()
    private LocalDateTime date_added;

    public Inventory(){

    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public Condition getBook_condition() {
        return book_condition;
    }

    public void setBook_condition(Condition book_condition) {
        this.book_condition = book_condition;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDate_added() {
        return date_added;
    }

    public void setDate_added(LocalDateTime date_added) {
        this.date_added = date_added;}
    }

