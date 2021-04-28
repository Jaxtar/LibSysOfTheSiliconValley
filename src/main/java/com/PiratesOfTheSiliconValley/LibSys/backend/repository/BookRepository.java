package com.PiratesOfTheSiliconValley.LibSys.backend.repository;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
