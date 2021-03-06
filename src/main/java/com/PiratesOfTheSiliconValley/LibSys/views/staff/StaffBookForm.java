package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.InventoryController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
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

import java.time.LocalDateTime;
import java.util.List;

public class StaffBookForm extends FormLayout {

    private Book book;
    private InventoryController inventoryController;

    TextField isbn = new TextField("ISBN");
    TextField title = new TextField("Titel");
    TextField author = new TextField("Författare");
    ComboBox<Book.Language> language = new ComboBox<>("Språk");
    ComboBox<Book.Format> format = new ComboBox<>("Format");
    ComboBox<Book.Genre> genre1 = new ComboBox<>("Genre");
    ComboBox<Book.Genre> genre2 = new ComboBox<>("Genre");
    TextArea description = new TextArea("Beskrivning");
    IntegerField pages = new IntegerField("Antal sidor");
    IntegerField publishingyear = new IntegerField("Utgivningsår");
    TextField publisher = new TextField("Utgivare");
    NumberField price = new NumberField("Pris");

    Binder<Book> bookBinder = new BeanValidationBinder<>(Book.class);

    Button save = new Button("Spara");
    Button delete = new Button("Radera");
    Button close = new Button("Avbryt");
    Button inventory = new Button("Lägg till i lager");

    public StaffBookForm(InventoryController inventoryController, List<Book> books) {
        this.inventoryController = inventoryController;
        addClassName("book-form");

        bookBinder.bindInstanceFields(this);
        bookBinder.addValueChangeListener(e -> save.setEnabled(bookBinder.isValid()));

        language.setItems(Book.Language.values());
        format.setItems(Book.Format.values());
        genre1.setItems(Book.Genre.values());
        genre2.setItems(Book.Genre.values());

        add(createButtonsLayout(inventoryController), inventoryButtonsLayout(),
            isbn, title, author, language, format,
            genre1, genre2, description, pages,
            publishingyear, publisher,  price);

    }

    private HorizontalLayout inventoryButtonsLayout() {
        Dialog dialog2 = new Dialog();
        dialog2.add(new Text("Vill du lägga till ett exemplar av den här boken i bibliotekets inventarium?"));
        dialog2.setCloseOnEsc(false);
        dialog2.setCloseOnOutsideClick(false);

        Button confirmButton2 = new Button("Ja", event -> {
            saveInventory();
            UI.getCurrent()
                    .navigate(StaffInventoryView.class);
            dialog2.close();
        });
        confirmButton2.addThemeVariants(ButtonVariant.LUMO_SUCCESS
        );
        Button avbryt2 = new Button("Avbryt", event ->
                dialog2.close()
        );

        dialog2.add(new Div(confirmButton2, avbryt2));

        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        inventory.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        close.addClickShortcut(Key.ESCAPE);

        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        inventory.addClickListener(e -> dialog2.open());

        return new HorizontalLayout(inventory);
    }

    private HorizontalLayout createButtonsLayout(InventoryController inventoryController) {
        this.inventoryController = inventoryController;


        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        inventory.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> dialogCancel().open());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        inventory.addClickListener(e -> dialogInventary().open());

        save.setEnabled(false);

        bookBinder.addStatusChangeListener(e -> save.setEnabled(bookBinder.isValid()));  //

        return new HorizontalLayout(save, delete, close, inventory);
    }

    private Dialog dialogInventary(){
        Dialog dialog = new Dialog();
        dialog.add(new Text("Vill du lägga en exemplar av den här boken i bibliotekens inventarie?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton2 = new Button("Ja", event -> {
            saveInventory();
            UI.getCurrent()
                    .navigate(StaffInventoryView.class);
            dialog.close();
        });
        
        confirmButton2.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        Button avbryt2 = new Button("Avbryt", event ->
                dialog.close()
        );

        dialog.add(new Div(confirmButton2, avbryt2));
        return dialog;
    }

    private Dialog dialogCancel(){
        Dialog dialog = new Dialog();
        dialog.add(new Text("Är du säkert att du vill radera boken från listan?"));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Confirm", event -> {
            fireEvent(new DeleteEvent(this, book));
            dialog.close();
        });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS
        );
        Button avbrytButton = new Button("Avbryt", event ->
                dialog.close()
        );

        dialog.add(new Div( confirmButton, avbrytButton));
        return dialog;
    }

    private void validateAndSave() {
        try {
            bookBinder.writeBean(book);
            fireEvent(new SaveEvent(this, book));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void saveInventory(){
        Inventory inventory = new Inventory();
        inventory.setIsbn(isbn.getValue());
        inventory.setTitle(title.getValue());
        inventory.setBook_condition(Inventory.Condition.PERFEKT);
        inventory.setStatus(Inventory.Status.INNE);
        inventory.setDate_added(LocalDateTime.now());
        inventoryController.save(inventory);
    }

    public void setBook(Book book) {
        this.book = book;
        bookBinder.readBean(book);
    }


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