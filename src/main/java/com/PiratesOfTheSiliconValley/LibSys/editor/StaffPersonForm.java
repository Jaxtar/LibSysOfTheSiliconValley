package com.PiratesOfTheSiliconValley.LibSys.editor;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Person;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;


public class StaffPersonForm extends FormLayout {

    private Person person;

    TextField personal_id_number = new TextField("Personal ID Number");
    TextField firstname = new TextField("Firstname");
    TextField lastname = new TextField("Lastname");
    TextField streetaddress = new TextField("Street Address");
    TextField postalcode = new TextField("PostalCode");
    TextField city = new TextField("City");
    TextField email = new TextField("Email");
    TextField phone = new TextField("Phone");


    Binder<Person> binder = new BeanValidationBinder<>(Person.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public StaffPersonForm() {
        addClassName("user-form");
        binder.bindInstanceFields(this);

        add(personal_id_number, firstname, lastname,
                streetaddress, postalcode, city, email, phone,
                createButtonsLayout());
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, person)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(person);
            fireEvent(new SaveEvent(this, person));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setUser(Person person) {
        this.person = person;
        binder.readBean(person);
    }

    // Events
    public static abstract class StaffPersonFormEvent extends ComponentEvent<StaffPersonForm> {
        private Person person;

        protected StaffPersonFormEvent(StaffPersonForm source, Person person) {
            super(source, false);
            this.person = person;
        }

        public Person getPerson() {
            return person;
        }
    }

    public static class SaveEvent extends StaffPersonFormEvent {
        SaveEvent(StaffPersonForm source, Person person) {
            super(source, person);
        }
    }

    public static class DeleteEvent extends StaffPersonFormEvent {
        DeleteEvent(StaffPersonForm source, Person person) {
            super(source, person);
        }

    }

    public static class CloseEvent extends StaffPersonFormEvent {
        CloseEvent(StaffPersonForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}