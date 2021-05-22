package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
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
    ComboBox<Book.Language> language = new ComboBox<>("Language");
    ComboBox<Book.Genre> genre1 = new ComboBox<>("Genre");
    ComboBox<Book.Genre> genre2 = new ComboBox<>("Genre");
    ComboBox<Book.Format> format = new ComboBox<>("Format");
    IntegerField pages = new IntegerField("Pages");
    TextField publisher = new TextField("Publisher");
    IntegerField publishingyear = new IntegerField("Publishing Year");
    TextField isbn = new TextField("ISBN");

    Binder<Book> binder = new BeanValidationBinder<>(Book.class);

    Button save = new Button("Spara");
    Button delete = new Button("Radera");
    Button close = new Button("Avbryt");

    public StaffBookForm() {
        addClassName("book-form");
        binder.bindInstanceFields(this);
        binder.addValueChangeListener(e -> save.setEnabled(binder.isValid()));

        language.setItems(Book.Language.values());
        genre1.setItems(Book.Genre.values());
        genre2.setItems(Book.Genre.values());
        format.setItems(Book.Format.values());

        add(createButtonsLayout(),title, author, description,
            language, genre1, genre2, format,
            pages, publisher, publishingyear, isbn
                );
        
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

        save.setEnabled(false);
        

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
            Notification.show(book.getTitle() + " har raderats från lista.", 1500,
                    Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_ERROR);
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