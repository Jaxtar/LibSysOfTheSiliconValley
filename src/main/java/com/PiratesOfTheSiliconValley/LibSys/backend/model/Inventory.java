package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {

    public enum Condition{
        PERFEKT{            public String toString(){return "Perfekt";}},
        BRA{                public String toString(){return "Bra";}},
        SLITEN{             public String toString(){return "Sliten";}},
        TRASIG{             public String toString(){return "Trasig";}}
    }

    public enum Status{
        INNE{               public String toString(){return "Inne";}},
        UTLÅNAD{            public String toString(){return "Utlånad";}},
        RESERVERAD{         public String toString(){return "Reserverad";}},
        STULEN{             public String toString(){return "Stulen";}},
        FÖRSVUNNEN{         public String toString(){return "Försvunnen";}}
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer bookID;

    @NotNull
    private String isbn;

    @NotNull
    private String title;

    private String classification;

    @Enumerated(EnumType.STRING)

    @NotNull(message = "Rutan får inte vara tom")
    private Inventory.Condition book_condition;

    @Enumerated(EnumType.STRING)
    @NotNull()
    private Inventory.Status status;

    @NotNull()
    private LocalDateTime date_added;

    
    //Getters and setters
    public Integer getBookID() {
        return bookID;
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

