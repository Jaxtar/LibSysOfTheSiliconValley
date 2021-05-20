package com.PiratesOfTheSiliconValley.LibSys.editor;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class StaffBookForm extends FormLayout {

    private Book book;

    TextField title = new TextField("Title");
    TextField author = new TextField("Author");
    TextArea description = new TextArea("Description");
    TextField language = new TextField("Language");
    TextField genre1 = new TextField("Genre 1");
    TextField genre2 = new TextField("Genre 2");
    ComboBox<Book.Format> format = new ComboBox<>("Format");
    IntegerField pages = new IntegerField("Pages");
    TextField publisher = new TextField("Publisher");
    IntegerField publishingyear = new IntegerField("Publishing Year");
    TextField isbn = new TextField("ISBN");

    Binder<Book> binder = new BeanValidationBinder<>(Book.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public StaffBookForm() {
        addClassName("book-form");
        binder.bindInstanceFields(this);

        format.setItems(Book.Format.values());

        add(title, author, description,
            language, genre1, genre2, format,
            pages, publisher, publishingyear, isbn,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, book)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
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