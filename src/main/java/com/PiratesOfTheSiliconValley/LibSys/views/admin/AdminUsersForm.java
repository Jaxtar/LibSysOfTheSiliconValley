package com.PiratesOfTheSiliconValley.LibSys.views.admin;

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


public class AdminUsersForm extends FormLayout {

    private User user;

    TextField personal_id_number = new TextField("Personal ID Number");
    TextField firstname = new TextField("Firstname");
    TextField lastname = new TextField("Lastname");
    TextField phone = new TextField("Phone");
    ComboBox<Role> role = new ComboBox<Role>("Role");
    TextField username = new TextField("Username");
    PasswordField passwordHash = new PasswordField("Password");

    Binder<User> binder = new BeanValidationBinder<>(User.class);

    Button save = new Button("Save");
    Button delete = new Button("Delete");
    Button close = new Button("Cancel");


    public AdminUsersForm() {
        addClassName("user-form");
        role.setItems(Role.values());

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
    public static abstract class AdminUserFormEvent extends ComponentEvent<AdminUsersForm> {
        private User user;

        protected AdminUserFormEvent(AdminUsersForm source, User user) {
            super(source, false);
            this.user = user;
        }

        public User getUser() {
            return user;
        }
    }

    public static class SaveEvent extends AdminUserFormEvent {
        SaveEvent(AdminUsersForm source, User user) {
            super(source, user);
        }
    }

    public static class DeleteEvent extends AdminUserFormEvent {
        DeleteEvent(AdminUsersForm source, User user) {
            super(source, user);
            Notification.show(user.getFirstname() + " " + user.getLastname() + " har raderats från lista.",
                    1500, Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_ERROR);
        }

    }

    public static class CloseEvent extends AdminUserFormEvent {
        CloseEvent(AdminUsersForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }

}