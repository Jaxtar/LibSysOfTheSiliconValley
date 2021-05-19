package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.BookController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.PiratesOfTheSiliconValley.LibSys.editor.StaffBookForm;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

//@Route(value = "/staffbook", layout = StaffLayout.class)
@PageTitle("Staffbook")
@CssImport("./views/staffview/staffcommon.css")

public class StaffBookView  extends VerticalLayout {

    private BookController bookController;
    private StaffBookForm staffBookForm;

    private Grid<Book> grid = new Grid<>(Book.class);
    private TextField filterText = new TextField();

    public StaffBookView(BookController bookController) {
        this.bookController = bookController;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        staffBookForm = new StaffBookForm();
        staffBookForm.addListener(StaffBookForm.SaveEvent.class, this::saveBook);
        staffBookForm.addListener(StaffBookForm.DeleteEvent.class, this::deleteBook);
        staffBookForm.addListener(StaffBookForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, staffBookForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        addClassName("book-grid");
        grid.setSizeFull();
        grid.setColumns("title", "author", "language", "format", "pages");
        //grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editBook(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by title...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addBookButton = new Button("Add Book");
        addBookButton.addClickListener(click -> addBook());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addBookButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void addBook() {
        grid.asSingleSelect().clear();
        editBook(new Book());
    }

    private void closeEditor() {
        staffBookForm.setBook(null);
        staffBookForm.setVisible(false);
        removeClassName("editing");
    }

    public void editBook(Book book) {
        if (book == null) {
            closeEditor();
        } else {
            staffBookForm.setBook(book);
            staffBookForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveBook(StaffBookForm.SaveEvent event) {
        bookController.save(event.getBook());
        updateList();
        closeEditor();
    }

    private void deleteBook(StaffBookForm.DeleteEvent event) {
        bookController.delete(event.getBook());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(bookController.findAll(filterText.getValue()));
    }


}
