package com.PiratesOfTheSiliconValley.LibSys.backend.repository;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    List<Book> findAll();
    List<Book> findByTitleContainsIgnoreCase(String searchTerm);
    List<Book> findByIsbn(String isbn);
}
