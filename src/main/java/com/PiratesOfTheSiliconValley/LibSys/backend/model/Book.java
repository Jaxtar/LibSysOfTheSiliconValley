package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.*;
        import javax.validation.constraints.NotEmpty;
        import javax.validation.constraints.NotNull;

@Entity
public class Book {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer bookID;

    public enum Format {
        Pocket, Hardcover, Paperback
    }

    @NotNull
    @NotEmpty
    private String title = "";

    @NotNull
    @NotEmpty
    private String author = "";

    private String description = "";

    private String language = "";

    @NotNull
    @NotEmpty
    private String genre1 = "";

    private String genre2 = "";

    @Enumerated(EnumType.STRING)
    @NotNull
    private Book.Format format;

    @NotNull
    @NotEmpty
    private Integer pages;

    @NotNull
    @NotEmpty
    private String publisher = "";

    @NotNull
    @NotEmpty
    private Integer publishingyear;

    public Integer getBookID() {
        return bookID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getGenre1() {
        return genre1;
    }

    public void setGenre1(String genre1) {
        this.genre1 = genre1;
    }

    public String getGenre2() {
        return genre2;
    }

    public void setGenre2(String genre2) {
        this.genre2 = genre2;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getPublishingyear() {
        return publishingyear;
    }

    public void setPublishingyear(Integer publishingyear) {
        this.publishingyear = publishingyear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    @NotNull
    @NotEmpty
    private String isbn = "";

    @Override
    public String toString() {
        return title + " by " + author;
    }

}
