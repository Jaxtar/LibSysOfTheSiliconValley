package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.InventoryController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;

@SpringBootTest
public class StaffBookFormTest {

    private List<Book> books;
    private Book book;
    private InventoryController inventoryController;

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
        StaffBookForm form = new StaffBookForm(inventoryController, books);
        form.setBook(book);

        Assert.assertEquals("9789175034508", form.isbn.getValue());
        Assert.assertEquals("Offer utan ansikte", form.title.getValue());
        Assert.assertEquals("Stefan Ahnhem", form.author.getValue());
        Assert.assertEquals("SVENSKA", form.language.getValue().toString().toUpperCase(Locale.ROOT));
        Assert.assertEquals("POCKET", form.format.getValue().toString().toUpperCase(Locale.ROOT));
        Assert.assertEquals("FIKTION", form.genre1.getValue().toString().toUpperCase(Locale.ROOT));
        Assert.assertEquals("En träslöjdslärare hittas brutalt mördad i sin slöjdsal.....",
                form.description.getValue());
        Assert.assertEquals("590", form.pages.getValue().toString());
        Assert.assertEquals("2014", form.publishingyear.getValue().toString());
        Assert.assertEquals("Månpocket", form.publisher.getValue());
        Assert.assertEquals("69.0", form.price.getValue().toString());
    }

    @Test
    public void saveCorrectValue() {
        StaffBookForm form = new StaffBookForm((inventoryController), books);
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
        form.addListener(StaffBookForm.SaveEvent.class, e -> savedBookRef.set(e.getBook()));
        form.save.click();

        Book savedBook = savedBookRef.get();

        Assert.assertEquals("9789146218807", savedBook.getIsbn());
        Assert.assertEquals("Tusen strålande solar", savedBook.getTitle());
        Assert.assertEquals("Khaled Hosseini", savedBook.getAuthor());
        Assert.assertEquals(Book.Language.SVENSKA, savedBook.getLanguage());
        Assert.assertEquals(Book.Format.POCKET, savedBook.getFormat());
        Assert.assertEquals(Book.Genre.SKÖNLITTERATUR, savedBook.getGenre1());
        Assert.assertEquals("Mariam är bara femton år gammal, fattig och utan utbildning...", savedBook.getDescription());
        Assert.assertEquals("406", savedBook.getPages().toString());
        Assert.assertEquals("2007", savedBook.getPublishingyear().toString());
        Assert.assertEquals("Wahlström & Widstrand", savedBook.getPublisher());
        Assert.assertEquals("71.0", savedBook.getPrice().toString());


    }
}