package com.PiratesOfTheSiliconValley.LibSys.backend.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
public class Book {

    public enum Language{
        SVENSKA{            public String toString(){return "Svenska";}},
        ENGELSKA{           public String toString(){return "English";}},
        ARABISKA{           public String toString(){return "العربية";}},
        ITALIENSKA{         public String toString(){return "Italiano";}},
        FRANSKA{            public String toString(){return "Français";}},
        SPANSKA{            public String toString(){return "Español";}},
        TYSKA{              public String toString(){return "Deutsche";}},
        JAPANSKA{           public String toString(){return "日本語";}},
        FINSKA{             public String toString(){return "Suomi";}}
    }

    public enum Format{
        POCKET{             public String toString(){return "Pocket";}}, 
        HARDCOVER{          public String toString(){return "Hard Cover";}}, 
        PAPERBACK{          public String toString(){return "Paperback";}}
    }

    public enum Genre {
        SKÖNLITTERATUR{     public String toString(){return "Skönlitteratur";}}, 
        FIKTION{            public String toString(){return "Fiktion";}}, 
        SCIFI{              public String toString(){return "Science Fiction";}}, 
        FILOSOFI{           public String toString(){return "Filosofi";}}, 
        FANTASY{            public String toString(){return "Fantasy";}}, 
        VETENSKAPOCHTEKNIK{ public String toString(){return "Vetenskap & Teknik";}}, 
        SJÄLVBIOGRAFI{      public String toString(){return "Självbiografi";}}, 
        KONST{              public String toString(){return "Konst";}}, 
        KLASSIKER{          public String toString(){return "Klassiker";}}, 
        DECKARE{            public String toString(){return "Deckare";}},
        MUSIK{              public String toString(){return "Musik";}}
    }

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer bookID;

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String title = "";

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String author = "";

    private String description = "";

    @Enumerated(EnumType.STRING)
    private Book.Language language;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Book.Genre genre1;

    @Enumerated(EnumType.STRING)
    private Book.Genre genre2;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Rutan får inte vara tom")
    private Book.Format format;

    @NotNull
    private Integer pages;

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String publisher = "";

    @NotNull
    private Integer publishingyear;

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String isbn = "";

    public Book(Integer bookID, String title,
                String author, String description,
                Book.Language language, Book.Genre genre1,
                Book.Genre genre2, Book.Format format,
                Integer pages, String publisher,
                Integer publishingyear, String isbn) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.description = description;
        this.language = language;
        this.genre1 = genre1;
        this.genre2 = genre2;
        this.format = format;
        this.pages = pages;
        this.publisher = publisher;
        this.publishingyear = publishingyear;
        this.isbn = isbn;
    }

    public Book(){
        
    }

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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Genre getGenre1() {
        return genre1;
    }

    public void setGenre1(Genre genre) {
        this.genre1 = genre;
    }

    public Genre getGenre2() {
        return genre2;
    }

    public void setGenre2(Genre genre) {
        this.genre2 = genre;
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
  
    @Override
    public String toString() {
        return title + " by " + author;
    }

}

