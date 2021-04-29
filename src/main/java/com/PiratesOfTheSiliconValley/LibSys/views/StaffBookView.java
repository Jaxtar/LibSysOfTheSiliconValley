package com.PiratesOfTheSiliconValley.LibSys.views;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.BookController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.littemplate.LitTemplate;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/staff/books", layout = StaffLayout.class)
@PageTitle("Biblioteket Jisho")
@CssImport("./views/staffview/staffmain.css")
@Tag("staff-book")

public class StaffBookView  extends VerticalLayout {

    private BookController bookController;
    private Grid<Book> grid = new Grid<>(Book.class);

    public StaffBookView(BookController bookController) {
        this.bookController = bookController;
        setSizeFull();
        configureGrid();

        add(grid);
        updateList();
    }

    private void configureGrid() {
        grid.setSizeFull();
        grid.setColumns("title", "author", "language", "format", "pages");

    }

    private void updateList() {
        grid.setItems(bookController.findAll());
    }


}
