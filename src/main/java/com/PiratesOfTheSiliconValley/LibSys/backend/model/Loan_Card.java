package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Loan_Card {

    public enum Status{
        DISABLED{             public String toString(){return "Spärrad";}},
        ENABLED{          public String toString(){return "Aktiverad";}}
    }

    public enum Reason{
        LATE{             public String toString(){return "Många sena böcker.";}},
        LOST{          public String toString(){return "Många försvunna böcker.";}},
        THEFT{          public String toString(){return "Stöld.";}}
    }

    @Id
    private Integer card_id;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Rutan får inte vara tom")
    private Loan_Card.Status status;

    @Enumerated(EnumType.STRING)
    private Loan_Card.Reason reason;

    public Loan_Card(Integer card_id, Loan_Card.Status status, Loan_Card.Reason reason) {
        this.card_id = card_id;
        this.status = status;
        this.reason = reason;
    }

    public Loan_Card(){
    }

    public Integer getCard_id() {
        return card_id;
    }

    public void setCard_id(Integer card_id) {
        this.card_id = card_id;
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

    @Override
    public String toString() {
        return "Kort " + status + ". Anledning: " + reason;
    }
}
