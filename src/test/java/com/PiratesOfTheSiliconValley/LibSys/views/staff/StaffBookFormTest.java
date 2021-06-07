package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.InventoryController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
class StaffBookFormTest {

    private List<Book> books;
    private Book book;

    @Before
    public void setUpData(){

        books = new ArrayList<>();
        book = new Book();

        book.setIsbn("9789175034508");
        book.setTitle("Offer utan ansikte");
        book.setAuthor("Stefan Ahnhem");
        book.setLanguage(Book.Language.SVENSKA);
        book.setFormat(Book.Format.POCKET);
        book.setGenre1(Book.Genre.FIKTION);
        book.setDescription("En träslöjdslärare hittas brutalt mördad i sin slöjdsal.....");
        book.setPages(590);
        book.setPublishingyear(2014);
        book.setPublisher("Månpocket");
        book.setPrice(69.0);

        books.add(book);

    }

    @Test
    public void formFieldsPopulated() {

        StaffBookForm form = new StaffBookForm((InventoryController) books);
        form.setBook(book);

        Assertions.assertEquals("9789175034508", form.isbn.getValue());
        Assertions.assertEquals("Offer utan ansikte", form.title.getValue());
        Assertions.assertEquals("Stefan Ahnhem", form.author.getValue());
        Assertions.assertEquals("SVENSKA", form.language.getValue());
        Assertions.assertEquals("POCKET", form.format.getValue());
        Assertions.assertEquals("FIKTION", form.genre1.getValue());
        Assertions.assertEquals("En träslöjdslärare hittas brutalt mördad i sin slöjdsal.....",
                form.description.getValue());
        Assertions.assertEquals(590, form.pages.getValue());
        Assertions.assertEquals(2014, form.publishingyear.getValue());
        Assertions.assertEquals("Månpocket", form.publisher.getValue());
        Assertions.assertEquals(69.0, form.price.getValue());
    }

    @Test
    void saveCorrectValue() {
        StaffBookForm form = new StaffBookForm((InventoryController) books);
        Book book = new Book();
        form.setBook(book);

        form.isbn.setValue("9789146218807");
        form.title.setValue("Tusen strålande solar");
        form.author.setValue("Khaled Hosseini");
        form.language.setValue(Book.Language.SVENSKA);
        form.format.setValue(Book.Format.POCKET);
        form.genre1.setValue(Book.Genre.SKÖNLITTERATUR);
        form.description.setValue("Mariam är bara femton år gammal, fattig och utan utbildning...");
        form.pages.setValue(406);
        form.publishingyear.setValue(2007);
        form.publisher.setValue("Wahlström & Widstrand");
        form.price.setValue(71.0);

        AtomicReference<Book> savedBookRef = new AtomicReference<>(null);
        form.addListener(StaffBookForm.SaveEvent.class, e ->{
            savedBookRef.set(e.getBook());
        });
        form.save.click();

        Book savedBook = savedBookRef.get();

        Assertions.assertEquals("9789146218807", savedBook.getIsbn());
        Assertions.assertEquals("Tusen strålande solar", savedBook.getTitle());
        Assertions.assertEquals("Khaled Hosseini", savedBook.getAuthor());
        Assertions.assertEquals(Book.Language.SVENSKA, savedBook.getLanguage());
        Assertions.assertEquals(Book.Format.POCKET, savedBook.getFormat());
        Assertions.assertEquals(Book.Genre.SKÖNLITTERATUR, savedBook.getGenre1());
        Assertions.assertEquals("Mariam är bara femton år gammal, fattig och utan utbildning...", savedBook.getDescription());
        Assertions.assertEquals(406, savedBook.getPages());
        Assertions.assertEquals(2007, savedBook.getPublishingyear());
        Assertions.assertEquals("Wahlström & Widstrand", savedBook.getPublisher());
        Assertions.assertEquals(71.0, savedBook.getPrice());


    }
}