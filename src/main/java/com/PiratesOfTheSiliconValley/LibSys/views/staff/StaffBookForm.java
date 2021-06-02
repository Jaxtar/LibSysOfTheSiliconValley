package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class StaffBookForm extends FormLayout {

    private Book book;

    TextField isbn = new TextField("ISBN");
    TextField title = new TextField("Title");
    TextField author = new TextField("Author");
    ComboBox<Book.Language> language = new ComboBox<>("Language");
    ComboBox<Book.Format> format = new ComboBox<>("Format");
    ComboBox<Book.Genre> genre1 = new ComboBox<>("Genre");
    ComboBox<Book.Genre> genre2 = new ComboBox<>("Genre");
    TextArea description = new TextArea("Description");
    IntegerField pages = new IntegerField("Pages");
    IntegerField publishingyear = new IntegerField("Publishing Year");
    TextField publisher = new TextField("Publisher");
    NumberField price = new NumberField("Price");

    Binder<Book> binder = new BeanValidationBinder<>(Book.class);

    Button save = new Button("Spara");
    Button delete = new Button("Radera");
    Button close = new Button("Avbryt");
    Button inventory = new Button("Lägg till lager");

    public StaffBookForm() {
        addClassName("book-form");
        binder.bindInstanceFields(this);
        binder.addValueChangeListener(e -> save.setEnabled(binder.isValid()));

        language.setItems(Book.Language.values());
        format.setItems(Book.Format.values());
        genre1.setItems(Book.Genre.values());
        genre2.setItems(Book.Genre.values());


        add(createButtonsLayout(),isbn, title, author,
            language,  format, genre1, genre2, description,
            pages, publishingyear, publisher,  price);
    }


    private HorizontalLayout createButtonsLayout() {
        Dialog dialog = new Dialog();
        dialog.add(new Text("Är du säkert att du vill radera boken från listan?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Confirm", event -> {
            fireEvent(new DeleteEvent(this, book));
            dialog.close();
        });
        Button cancelButton = new Button("Cancel", event ->
            dialog.close()
        );

        dialog.add(new Div( confirmButton, cancelButton));


        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        inventory.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> dialog.open());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        inventory.addClickListener(e -> UI.getCurrent()
                .navigate(AddBookToInventoryView.class));

        save.setEnabled(false);

        return new HorizontalLayout(save, delete, close, inventory);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(book);
            fireEvent(new SaveEvent(this, book));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setBook(Book book) {
        this.book = book;
        binder.readBean(book);
    }

    // Events
    public static abstract class StaffBookFormEvent extends ComponentEvent<StaffBookForm> {
        private Book book;

        protected StaffBookFormEvent(StaffBookForm source, Book book) {
            super(source, false);
            this.book = book;
        }

        public Book getBook() {
            return book;
        }
    }

    public static class SaveEvent extends StaffBookFormEvent {
        SaveEvent(StaffBookForm source, Book book) {
            super(source, book);
        }
    }

    public static class DeleteEvent extends StaffBookFormEvent {
        DeleteEvent(StaffBookForm source, Book book) {
            super(source, book);
        }
    }

    public static class CloseEvent extends StaffBookFormEvent {
        CloseEvent(StaffBookForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}