package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
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
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;
import org.springframework.dao.DataIntegrityViolationException;

public class StaffUsersForm extends FormLayout {

    private User user;

    TextField personal_id_number = new TextField("Personnummer");
    TextField firstname = new TextField("Förnamn");
    TextField lastname = new TextField("Efternamn");
    TextField phone = new TextField("Telefon");
    ComboBox<Role> role = new ComboBox<Role>("Roll");
    TextField username = new TextField("Användarnamn");
    PasswordField passwordHash = new PasswordField("Lösenord");

    Binder<User> binder = new BeanValidationBinder<>(User.class);

    Button save = new Button("Spara");
    Button delete = new Button("Radera");
    Button close = new Button("Avbryt");


    public StaffUsersForm() {
        addClassName("user-form");
        role.setItems(Role.USER);

        binder.bindInstanceFields(this);

        add(personal_id_number, firstname, lastname, phone, username, passwordHash, role,
                createButtonsLayout());
    }

    public static class Bean {
        private Role field;

        public Role getField() {

            return field;
        }

        public void setField(Role field) {

            this.field = field;
        }
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, user)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));


        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid()));

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(user);
            fireEvent(new SaveEvent(this, user));
        } catch (ValidationException e) {
            e.printStackTrace();
        }catch (DataIntegrityViolationException e){
            e.printStackTrace();
            Notification.show(" Användarnamn används redan, försök med en ny.",
                    2000, Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    public void setUser(User user) {
        this.user = user;
        binder.readBean(user);
    }

    // Events
    public static abstract class StaffUserFormEvent extends ComponentEvent<StaffUsersForm> {
        private User user;

        protected StaffUserFormEvent(StaffUsersForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends StaffUserFormEvent {
        SaveEvent(StaffUsersForm source, User user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends StaffUserFormEvent {
        DeleteEvent(StaffUsersForm source, User user) {
            super(source, user);
            Notification.show(user.getFirstname() + " " + user.getLastname() + " har raderats från lista.",
                    1500, Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

    }

    public static class CloseEvent extends StaffUserFormEvent {
        CloseEvent(StaffUsersForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}