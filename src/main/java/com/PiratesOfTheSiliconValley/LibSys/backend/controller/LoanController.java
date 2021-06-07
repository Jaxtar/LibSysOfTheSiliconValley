package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.LoanRepository;

import org.springframework.stereotype.Service;

@Service
public class LoanController {
    private static final Logger LOGGER = Logger.getLogger(LoanController.class.getName());
    private LoanRepository loanRepository;

    public LoanController(LoanRepository loanRepository) {
        this.loanRepository = loanRepository;
    }

    public List<Loan> findAll() {
        return loanRepository.findAll();
    }

    public void delete(Loan loan) {
        loanRepository.delete(loan);
    }

    public void save(Loan loan) {
        if (loan == null) {
            LOGGER.log(Level.SEVERE,
                    "Loan is null. Are you sure you have connected your form to the application?");
            return;
        }
        loanRepository.save(loan);
    }
}
