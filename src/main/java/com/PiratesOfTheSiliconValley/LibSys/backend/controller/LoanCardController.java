package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan_Card;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.LoanCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LoanCardController {
    public static final Logger LOGGER = Logger.getLogger(LoanCardController.class.getName());
    private LoanCardRepository repository;

    public LoanCardController(LoanCardRepository repository) {
        this.repository = repository;
    }


    public List<Loan_Card> findAll(String reasonString){
        if (reasonString == null || reasonString.isEmpty()){
            return repository.findAll();
        } else {
            return repository.findByReason(reasonString);
        }
    }


    public void delete(Loan_Card loanCard) {
        repository.delete(loanCard);
    }

    public void save(Loan_Card loanCard) {
        if (loanCard == null) {
            LOGGER.log(Level.SEVERE,
                    "LoanCard is null. Are you sure you have connected your form to the application?");
            return;
        }
        repository.save(loanCard);
    }
}
