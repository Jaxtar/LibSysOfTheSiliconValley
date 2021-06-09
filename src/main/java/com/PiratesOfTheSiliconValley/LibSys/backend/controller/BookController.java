package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.BookRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


@Service
public class BookController {
    private static final Logger LOGGER = Logger.getLogger(BookController.class.getName());
    private BookRepository bookRepository;

    //Initializes the controller
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //Finds all of the books
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    //Finds all books given a specific search term
    public List<Book> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return bookRepository.findAll();
        } else {
            return bookRepository.findByTitleContainsIgnoreCase(stringFilter);
        }
    }

    //Deletes the given book
    public void delete(Book book) {
        bookRepository.delete(book);
    }

    //Saves the given book
    public void save(Book book) {
        if (book == null) {
            LOGGER.log(Level.SEVERE,
                    "Book is null. Are you sure you have connected your form to the application?");
            return;
        }
        bookRepository.save(book);
    }
}