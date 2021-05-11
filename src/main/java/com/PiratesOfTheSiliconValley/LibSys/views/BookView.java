package com.PiratesOfTheSiliconValley.LibSys.views;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.BookRepository;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.H1;
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

    public BookView(BookRepository repo){
        this.repo = repo;
    }

    @Override
    public void setParameter(BeforeEvent event, String param){
        getBook(Integer.parseInt(param));
    }

    private void getBook(int bookID){
        book = repo.findByBookID(bookID).get(0);
    }
}
