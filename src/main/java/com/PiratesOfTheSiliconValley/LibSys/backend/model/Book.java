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
    @NotNull
    @NotEmpty
    private String isbn;

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String title;

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String author;

    @Enumerated(EnumType.STRING)
    private Book.Language language;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Rutan får inte vara tom")
    private Book.Format format;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Book.Genre genre1;

    @Enumerated(EnumType.STRING)
    private Book.Genre genre2;

    private String description;

    @NotNull
    private Integer pages;

    private Integer publishingyear;

    @NotNull
    @NotEmpty(message = "Rutan får inte vara tom")
    private String publisher;

    private Double price;


    public Book(String isbn, String title, String author, Book.Language language,
                Book.Format format, Book.Genre genre1, Book.Genre genre2, String description,
                Integer pages, Integer publishingyear, String publisher, Double price) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.language = language;
        this.format = format;
        this.genre1 = genre1;
        this.genre2 = genre2;
        this.description = description;
        this.pages = pages;
        this.publishingyear = publishingyear;
        this.publisher = publisher;
        this.price = price;
    }

    public Book(){
        
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Integer getPublishingyear() {
        return publishingyear;
    }

    public void setPublishingyear(Integer publishingyear) {
        this.publishingyear = publishingyear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
  
    @Override
    public String toString() {
        return title + " by " + author;
    }

}

