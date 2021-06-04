package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "decommissioned")
public class Decommissioned {

    public enum Condition{
        PERFEKT{ public String toString(){return "perfekt";}},
        BRA{ public String toString(){return "bra";}},
        SLITEN{ public String toString(){return "sliten";}},
        TRASIG{ public String toString(){return "trasig";}}
    }

    @Id
    private Integer bookID;

    public Integer getBookID() {
            return bookID;
        }

    @NotNull
    @NotEmpty
    private String book_isbn = "";

    @NotNull
    private String title = "";

    private String classification;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Rutan får inte vara tom")
    private Decommissioned.Condition condition;

    private LocalDateTime date_added; //= Date.valueOf(LocalDate.now());

    private LocalDateTime date_removed;

    private String reason;

    public Decommissioned(Integer bookID, String book_isbn, String title, String classification, Decommissioned.Condition condition,
                          LocalDateTime date_added, LocalDateTime date_removed, String reason){
            this.bookID = bookID;
            this.book_isbn = book_isbn;
            this.title = title;
            this.classification = classification;
            this.condition = condition;
            this.date_added = date_added;
            this.date_removed = date_removed;
            this.reason = reason;
        }

        public Decommissioned(){

        }

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

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
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
}
