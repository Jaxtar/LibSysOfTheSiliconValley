package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "inventory")
public class Inventory {

    public enum Condition{
        PERFEKT{ public String toString(){return "Perfekt";}},
        BRA{ public String toString(){return "Bra";}},
        SLITEN{ public String toString(){return "Sliten";}},
        TRASIG{ public String toString(){return "Trasig";}}
    }

    public enum Status{
        INNE{ public String toString(){return "Inne";}},
        UTLÅNAD{ public String toString(){return "Utlånad";}},
        RESERVERAD{ public String toString(){return "Reserverad";}},
        STULEN{ public String toString(){return "Stulen";}},
        FÖRSVUNNEN{ public String toString(){return "Försvunnen";}}
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer bookID;

    public Integer getBookID() {
        return bookID;
    }

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String isbn = "";

    @NotNull
    @NotEmpty
    private String title = "";

    private String classification;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Rutan får inte vara tom")
    private Inventory.Condition book_condition;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Rutan får inte vara tom")
    private Inventory.Status status;

    private LocalDateTime date_added; //= Date.valueOf(LocalDate.now());

    public Inventory(Integer bookID, String isbn, String title, String classification, Inventory.Condition condition,
                     Inventory.Status status, LocalDateTime date_added){
        this.bookID = bookID;
        this.isbn = isbn;
        this.title = title;
        this.classification = classification;
        this.book_condition = condition;
        this.status = status;
        this.date_added = date_added;
    }

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

    public Condition getBook_Condition() {
        return book_condition;
    }

    public void setBook_Condition(Condition condition) {
        this.book_condition = condition;
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
        this.date_added = date_added;//LocalDateTime.now();
    }
}
