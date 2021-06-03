package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Text;
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

public class StaffInventoryForm extends FormLayout {

    private Inventory inventory;

    TextField isbn = new TextField("ISBN");
    TextField title = new TextField("Title");
    TextField classification = new TextField("Classification");
    ComboBox<Inventory.Condition> condition = new ComboBox<>("Condition");
    ComboBox<Inventory.Status> status = new ComboBox<>("Status");
    //LocalDateTime date = LocalDateTime.now();


    Binder<Inventory> binder = new BeanValidationBinder<>(Inventory.class);

    Button save = new Button("Spara");
    Button close = new Button("Avbryt");
    Button radera = new Button("Radera");

    public StaffInventoryForm() {
        addClassName("book-form");
        binder.bindInstanceFields(this);
        binder.addValueChangeListener(e -> save.setEnabled(binder.isValid()));

        condition.setItems(Inventory.Condition.values());
        status.setItems(Inventory.Status.values());


        add(createButtonsLayout(), isbn, title, classification, condition, status);
    }

    private HorizontalLayout createButtonsLayout() {
        Dialog dialog = new Dialog();
        TextField reason = new TextField("Ange en anledning:");
        dialog.add(new Text("Är du säkert att du vill radera boken från lager?"));
        dialog.add(new VerticalLayout(reason));
        dialog.setCloseOnEsc(false);
        dialog.setCloseOnOutsideClick(false);

        Button confirmButton = new Button("Confirm", event -> {
            fireEvent(new DeleteEvent(this, inventory));
            dialog.close();
        });
        confirmButton.addThemeVariants(ButtonVariant.LUMO_SUCCESS
        );
        Button cancelButton = new Button("Cancel", event ->
            dialog.close()
        );

        dialog.add(new Div( confirmButton, cancelButton));


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
            //Inventory inventory = new Inventory();
            //inventory.setDate_added(date);
            binder.writeBean(inventory);
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