package com.PiratesOfTheSiliconValley.LibSys.backend.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Integer> {

    List<Loan> findByBookId(Integer book_id);
    List<Loan> findByCardId(Integer card_id);
    List<Loan> findByLoanDate(LocalDateTime loan_date);
    List<Loan> findByReturnDate(LocalDateTime return_date);
}
