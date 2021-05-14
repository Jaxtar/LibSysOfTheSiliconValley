package com.PiratesOfTheSiliconValley.LibSys.editor;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Staff;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

public class StaffForm extends FormLayout {

    private Staff staff;

    TextField firstname = new TextField("Namn");
    TextField lastname = new TextField("Efternamn");
    TextField username = new TextField("Användarnamn");
    TextField password = new TextField("Lösenord");
    TextField email = new TextField("Mail");
    ComboBox<Staff.Occupation> occupation = new ComboBox<>("Anställning");

    Binder<Staff> binder = new BeanValidationBinder<>(Staff.class);

    Button save = new Button ("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");

    public StaffForm(){
        addClassName("staff-form");
        binder.bindInstanceFields(this);

        occupation.setItems(Staff.Occupation.values());

        add(firstname, lastname, username, password, email, occupation, createButtonLayout());
    }

    private HorizontalLayout createButtonLayout(){
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new StaffForm.DeleteEvent(this, staff)));
        close.addClickListener(event -> fireEvent(new StaffForm.CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(staff);
            fireEvent(new StaffForm.SaveEvent(this, staff));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
        binder.readBean(staff);
    }

    public static abstract class StaffFormEvent extends ComponentEvent<StaffForm> {
        private Staff staff;

        protected StaffFormEvent(StaffForm source, Staff staff) {
            super(source, false);
            this.staff = staff;
        }

        public Staff getStaff() {
            return staff;
        }
    }

    public static class SaveEvent extends StaffForm.StaffFormEvent {
        SaveEvent(StaffForm source, Staff staff) {
            super(source, staff);
        }
    }

    public static class DeleteEvent extends StaffForm.StaffFormEvent {
        DeleteEvent(StaffForm source, Staff staff) {
            super(source, staff);
            Notification.show(staff.getFirstname() + " " + staff.getLastname() + " är raderat.", 1200, Notification.Position.MIDDLE );
        }
    }

    public static class CloseEvent extends StaffForm.StaffFormEvent {
        CloseEvent(StaffForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}
