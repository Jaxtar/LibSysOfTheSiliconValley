package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;
import com.vaadin.flow.component.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.shared.Registration;

@Route(value = "/staff/addtoinventory", layout = Navbar.class)
    @PageTitle("Add book to Inventory")
    public class AddBookToInventoryView extends Div {

    //private InventoryController inventoryController;
    //private InventoryRepository inventoryRepository;

    private Inventory inventory;

    TextField title = new TextField("Title");
    TextField isbn = new TextField("ISBN");
    TextField classification = new TextField("Classification");
    ComboBox<Inventory.Condition> condition = new ComboBox<>("Condition");
    ComboBox<Inventory.Status> status = new ComboBox<>("Status");

    Binder<Inventory> binder = new BeanValidationBinder<>(Inventory.class);

    private Button save = new Button("Spara");
    private Button cancel = new Button("Avbryt");



    public AddBookToInventoryView(){ //InventoryController inventoryController, InventoryRepository inventoryRepository) {
       // this.inventoryController = inventoryController;
       // this.inventoryRepository = inventoryRepository;
        add(createTitle());
        add(createFormLayout());
        //add(createButtonLayout());

        binder.bindInstanceFields(this);
        binder.addValueChangeListener(e -> save.setEnabled(binder.isValid()));

        condition.setItems(Inventory.Condition.values());
        status.setItems(Inventory.Status.values());


    }

    private Component createTitle() {
        return new H3("Lägg till en ny book i lager lista");
    }

    private Component createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(createButtonLayout(), title, isbn, classification, condition, status);
        return formLayout;
    }

    private HorizontalLayout createButtonLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        cancel.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        cancel.addClickShortcut(Key.ESCAPE);

        cancel.addClickListener(event -> UI.getCurrent()
                                .navigate(StaffBookView.class));
        save.addClickListener(event -> {
                              validateAndSave();
                              Notification.show(" Boken med är nu sparad i inventory", 1500,
                              Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                              UI.getCurrent().navigate(StaffInventoryView.class);
                              });

        return new HorizontalLayout(save, cancel);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(inventory);
            fireEvent(new SaveEvent(this, inventory));
            //new InventoryController(inventoryRepository).save(inventory);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
        binder.readBean(inventory);
    }

    public static abstract class AddBookToInventoryViewEvent extends ComponentEvent<AddBookToInventoryView> {
        private Inventory inventory;

        protected AddBookToInventoryViewEvent(AddBookToInventoryView source, Inventory inventory) {
            super(source, false);
            this.inventory = inventory;
        }

        public Inventory getInventory() {
            return inventory;
        }
    }

    public static class SaveEvent extends AddBookToInventoryView.AddBookToInventoryViewEvent {
        SaveEvent(AddBookToInventoryView source, Inventory inventory) {
            super(source, inventory);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}
