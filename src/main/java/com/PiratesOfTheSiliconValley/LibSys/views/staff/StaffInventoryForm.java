package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.DecommissionedController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Decommissioned;
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
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDateTime;

public class StaffInventoryForm extends FormLayout {

    private Inventory inventory;
    private DecommissionedController decommissionedController;

    TextField isbn = new TextField("ISBN");
    TextField title = new TextField("Titel");
    TextField classification = new TextField("Klassificering");
    ComboBox<Inventory.Condition> book_condition = new ComboBox<>("Skick");
    ComboBox<Inventory.Status> status = new ComboBox<>("Status");
    TextField reason = new TextField("Ange orsak:");

    Binder<Inventory> binder = new BeanValidationBinder<>(Inventory.class);

    Button save = new Button("Spara");
    Button close = new Button("Avbryt");
    Button radera = new Button("Radera");

    public StaffInventoryForm(DecommissionedController decommissionedController) {
        this.decommissionedController = decommissionedController;
        addClassName("book-form");
        binder.bindInstanceFields(this);
        binder.addValueChangeListener(e -> save.setEnabled(binder.isValid()));

        book_condition.setItems(Inventory.Condition.values());
        status.setItems(Inventory.Status.values());


        add(createButtonsLayout(this.decommissionedController), 
            isbn, title, classification, book_condition, status);
    }

    private HorizontalLayout createButtonsLayout(DecommissionedController decommissionedController) {
        this.decommissionedController = decommissionedController;
        Dialog dialog = new Dialog();
        dialog.add(reason);
        dialog.add(new Text("Är du säker på att du vill radera boken från lagret?"));
        dialog.add(new VerticalLayout(reason));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Ja", event -> {
            saveDecommissioned();
            UI.getCurrent()
                    .navigate(StaffDecommissionedView.class);
            dialog.close();
        });

        confirmButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS);

        Button avbrytButton = new Button("Avbryt", event -> dialog.close());

        dialog.add(new Div( confirmButton, avbrytButton));

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        radera.addThemeVariants(ButtonVariant.LUMO_ERROR);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));
        radera.addClickListener(event -> dialog.open());

        save.setEnabled(false);

        return new HorizontalLayout(save, close, radera);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(inventory);
            fireEvent(new SaveEvent(this, inventory));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    private void saveDecommissioned(){
        Decommissioned decommissioned = new Decommissioned();
        decommissioned.setBookID(inventory.getBookID());
        decommissioned.setBook_isbn(isbn.getValue());
        decommissioned.setTitle(title.getValue());
        decommissioned.setClassification(classification.getValue());
        decommissioned.setBook_condition(book_condition.getValue());
        decommissioned.setDate_added(inventory.getDate_added());
        decommissioned.setDate_removed(LocalDateTime.now());
        decommissioned.setReason(reason.getValue());
        decommissionedController.save(decommissioned);
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        binder.readBean(inventory);
    }

    
    public static abstract class StaffInventoryFormEvent extends ComponentEvent<StaffInventoryForm> {
        private Inventory inventory;

        protected StaffInventoryFormEvent(StaffInventoryForm source, Inventory inventory) {
            super(source, false);
            this.inventory = inventory;
        }

        public Inventory getInventory() {
            return inventory;
        }
    }

    public static class SaveEvent extends StaffInventoryForm.StaffInventoryFormEvent {
        SaveEvent(StaffInventoryForm source, Inventory inventory) {
            super(source, inventory);
        }
    }

    public static class DeleteEvent extends StaffInventoryForm.StaffInventoryFormEvent {
        DeleteEvent(StaffInventoryForm source, Inventory inventory) {
            super(source, inventory);
        }
    }

    public static class CloseEvent extends StaffInventoryForm.StaffInventoryFormEvent {
        CloseEvent(StaffInventoryForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
