package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.validation.constraints.NotNull;

@Entity
public class Loan {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer loanId;

    @NotNull
    private Integer bookId;
    
    private LocalDateTime loanDate;

    private LocalDateTime returnDate;

    @NotNull
    private Integer cardId;
    
    public Integer getLoanId() {
        return loanId;
    }

    public void setLoanId(Integer loan_id) {
        this.loanId = loan_id;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer book_id) {
        this.bookId = book_id;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer card_id) {
        this.cardId = card_id;
    }

    public LocalDateTime getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDateTime loan_date) {
        this.loanDate = loan_date;
    }

    public LocalDateTime getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDateTime return_date) {
        this.returnDate = return_date;
    }
}