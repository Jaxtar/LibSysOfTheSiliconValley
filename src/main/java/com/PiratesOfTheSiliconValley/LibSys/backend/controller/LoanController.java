package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.LoanRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class LoanController {
    private static final Logger LOGGER = Logger.getLogger(LoanController.class.getName());
    private LoanRepository loanRepository;

    //Initializes the controller
    public LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    //Finds all the loans
    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    //Findsd all the loans given the card id
    public List<Loan> findAll(Integer integerFilter) {
        if (integerFilter == null) {
            return loanRepository.findAll();
        } else {
            return loanRepository.findByCardId(integerFilter);
        }
    }

    //Removes a loan
    public void delete(Loan loan) {
        loanRepository.delete(loan);
    }

    //Saves a loan
    public void save(Loan loan) {
        if (loan == null) {
            LOGGER.log(Level.SEVERE,
                    "Loan is null. Are you sure you have connected your form to the application?");
            return;
        }
        loanRepository.save(loan);
    }
}
