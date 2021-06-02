package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.InventoryController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

    @Route(value = "/staff/addtoinventory", layout = Navbar.class)
    @PageTitle("Add book to Inventory")
    public class AddBookToInventoryView extends Div {

        TextField isbn = new TextField("ISBN");
        TextField classification = new TextField("Classification");
        ComboBox<Inventory.Condition> condition = new ComboBox<>("Condition");
        ComboBox<Inventory.Status> status = new ComboBox<>("Status");

        private Button cancel = new Button("Cancel");
        private Button save = new Button("Spara");

        private Binder<Inventory> binder = new BeanValidationBinder<>(Inventory.class);

        public AddBookToInventoryView(InventoryController inventoryController) {
            addClassName("add-book-to-inventory");

            add(createTitle());
            add(createFormLayout());
            add(createButtonLayout());
            binder.addValueChangeListener(e -> save.setEnabled(binder.isValid()));
            binder.bindInstanceFields(this);
            condition.setItems(Inventory.Condition.values());
            status.setItems(Inventory.Status.values());

            clearForm();

            cancel.addClickListener(e -> clearForm());
            save.addClickListener(e -> {
                inventoryController.save(binder.getBean());
                Notification.show(binder.getBean().getClass().getSimpleName() + " details stored.");
                clearForm();
            });
        }

        private void clearForm() {
            binder.setBean(new Inventory());
        }

        private Component createTitle() {
            return new H3("Lägg till en ny book i lager lista");
        }

        private Component createFormLayout() {
            FormLayout formLayout = new FormLayout();
            formLayout.add(isbn, classification, condition, status);
            return formLayout;
        }

        private Component createButtonLayout() {
            HorizontalLayout buttonLayout = new HorizontalLayout();
            buttonLayout.addClassName("button-layout");
            save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
            buttonLayout.add(save);
            buttonLayout.add(cancel);
            return buttonLayout;
        }

}
