package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.BookController;
import com.PiratesOfTheSiliconValley.LibSys.backend.controller.InventoryController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Route(value = "/staff/book", layout = Navbar.class)
@PageTitle("Böcker")
@CssImport("./views/staffview/staffcommon.css")

public class StaffBookView  extends VerticalLayout {

    BookController bookController;
    InventoryController inventoryController;
    StaffBookForm staffBookForm;

    Grid<Book> grid = new Grid<>(Book.class);

    TextField filterText = new TextField();

    public StaffBookView(BookController bookController, InventoryController inventoryController) {
        this.bookController = bookController;
        this.inventoryController = inventoryController;
      
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        staffBookForm = new StaffBookForm(this.inventoryController, bookController.findAll());

        staffBookForm.addListener(StaffBookForm.SaveEvent.class, this::saveBook);
        staffBookForm.addListener(StaffBookForm.DeleteEvent.class, this::deleteBook);
        staffBookForm.addListener(StaffBookForm.CloseEvent.class, e -> closeEditor());
        staffBookForm.setMinWidth("20em");

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
        grid.setColumns("title", "author", "language", "format", "pages", "price");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.getColumnByKey("title").setHeader("Titel");
        grid.getColumnByKey("author").setHeader("Författare");
        grid.getColumnByKey("language").setHeader("Språk");
        grid.getColumnByKey("format").setHeader("Format");
        grid.getColumnByKey("pages").setHeader("Antal sidor");
        grid.getColumnByKey("price").setHeader("Pris");

        grid.asSingleSelect().addValueChangeListener(event ->
                editBook(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Sök titel...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addBookButton = new Button("Lägg till bok");
        addBookButton.addClickListener(click -> addBook());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addBookButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void addBook() {
        grid.asSingleSelect().clear();
        editBook(new Book());
        staffBookForm.inventory.setVisible(false);
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
            staffBookForm.inventory.setVisible(book.getIsbn() != null);
        }
    }

    private void saveBook(StaffBookForm.SaveEvent event) {
        bookController.save(event.getBook());
        updateList();
        closeEditor();
        Notification.show("Boken är nu sparad.", 1500,
                Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
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
