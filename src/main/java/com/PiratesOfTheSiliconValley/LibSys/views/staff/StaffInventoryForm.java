package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.time.LocalDateTime;

public class StaffInventoryForm extends FormLayout {

    private Inventory inventory;

    TextField isbn = new TextField("ISBN");
    TextField classification = new TextField("Classification");
    ComboBox<Inventory.Condition> condition = new ComboBox<>("Condition");
    ComboBox<Inventory.Status> status = new ComboBox<>("Status");


    Binder<Inventory> binder = new BeanValidationBinder<>(Inventory.class);

    Button save = new Button("Spara");
    Button close = new Button("Avbryt");

    public StaffInventoryForm() {
        binder.addValueChangeListener(e -> save.setEnabled(binder.isValid()));

        binder.bindInstanceFields(this);

        condition.setItems(Inventory.Condition.values());
        status.setItems(Inventory.Status.values());

        add(createButtonsLayout(), isbn, classification, condition, status);
    }
    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        save.setEnabled(false);

        return new HorizontalLayout(save, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(inventory);
            LocalDateTime date = LocalDateTime.now();
            inventory.getDate_added();
            inventory.setDate_added(date);
            fireEvent(new SaveEvent(this, inventory));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
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
