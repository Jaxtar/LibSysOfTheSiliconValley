package com.PiratesOfTheSiliconValley.LibSys.views;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.BookRepository;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/book", layout = Navbar.class)
@PageTitle("Bok")
@CssImport("./views/mainview/main-page.css")
@Tag("item-view")
@JsModule("./views/mainview/main-page.ts")
public class BookView extends VerticalLayout implements HasUrlParameter<String>{
    private Book book;
    private BookRepository repo;

    private H1 title;
    private Image image;
    private H3 importantInfo;
    private Span description;
    private H5 remainingInfo;


    public BookView(BookRepository repo){
        this.repo = repo;

        this.image = new Image("images/books/bookNotFound.png", "Book Image");
        this.title = new H1();
        this.importantInfo = new H3();
        this.description = new Span();
        this.remainingInfo = new H5();
        
        add(image, title, importantInfo, description, remainingInfo);
    }

    @Override
    public void setParameter(BeforeEvent event, String param){
        getBook(Integer.parseInt(param));
        createView();
    }

    private void getBook(int bookID){
        book = repo.findByBookID(bookID).get(0);
    }

    private void createView(){
        String path = String.format("images/books/%s.png", book.getBookID());
        
        image.setSrc(path);
        image.setMaxHeight("20em");

        title.setText(book.getTitle());

        importantInfo.setText(String.format("av %s, släppt av %s år %s (%s)", 
                                            book.getAuthor(), 
                                            book.getPublisher(), 
                                            book.getPublishingyear(),
                                            book.getLanguage()));

        description.setText(book.getDescription());
        description.addClassName("desc");

        remainingInfo.setText(String.format("""
                                            %s
                                            Antal sidor: %s
                                            Format: %s
                                            ISBN: %s
                                            """,
                                            book.getGenre2() == null  
                                                                    ? "Genre: " + book.getGenre1() 
                                                                    : String.format("Genrer: %s, %s", book.getGenre1(), book.getGenre2()),
                                            book.getPages(),
                                            book.getFormat(),
                                            book.getIsbn()));
        remainingInfo.addClassName("info");
        
    }
}
