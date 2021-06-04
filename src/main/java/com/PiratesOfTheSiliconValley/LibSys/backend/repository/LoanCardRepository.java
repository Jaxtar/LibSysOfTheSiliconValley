package com.PiratesOfTheSiliconValley.LibSys.backend.repository;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan_Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanCardRepository extends JpaRepository<Loan_Card, Integer> {

    List<Loan_Card> findAll();
    List<Loan_Card> findByReason(String reasonString);
}
