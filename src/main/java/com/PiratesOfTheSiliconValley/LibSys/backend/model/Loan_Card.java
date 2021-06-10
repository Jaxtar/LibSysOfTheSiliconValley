package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Loan_Card {

    public enum Status{
        DISABLED{           public String toString(){return "Spärrad"; }},
        ENABLED{            public String toString(){return "Aktiverad";}}
    }

    public enum Reason{
        LATE{               public String toString(){return "Många sena böcker";}},
        LOST{               public String toString(){return "Många försvunna böcker";}},
        THEFT{              public String toString(){return "Stöld";}}
    }

    @Id
    private Integer cardId;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Rutan får inte vara tom")
    private Loan_Card.Status status;

    @Enumerated(EnumType.STRING)
    private Loan_Card.Reason reason;

    @Override
    public String toString() {
        return "Kort " + cardId + " " + status;
    }

    //Getters and setters
    public Integer getCard_id() {
        return cardId;
    }

    public void setCard_id(Integer card_id) {
        this.cardId = card_id;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }
}
