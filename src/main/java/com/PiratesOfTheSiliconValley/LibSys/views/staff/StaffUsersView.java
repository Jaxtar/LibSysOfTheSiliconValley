package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.UserController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Route(value = "/staff/users", layout = Navbar.class)
@PageTitle("Användare")
@CssImport("./views/staffview/staffcommon.css")
public class StaffUsersView extends VerticalLayout {

    UserController userController;
    StaffUsersForm userForm;

    Grid<User> grid = new Grid<>(User.class);
    TextField filterText = new TextField();

    public StaffUsersView(UserController userController) {
        this.userController = userController;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        userForm = new StaffUsersForm(userController.findAll());

        userForm.addListener(StaffUsersForm.SaveEvent.class, this::saveUser);
        userForm.addListener(StaffUsersForm.DeleteEvent.class, this::deleteUser);
        userForm.addListener(StaffUsersForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, userForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        addClassName("user-grid");
        grid.setSizeFull();
        grid.setColumns("personal_id_number", "firstname", "lastname",
                "phone", "username", "role");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.getColumnByKey("personal_id_number").setHeader("Personnummer");
        grid.getColumnByKey("firstname").setHeader("Förnamn");
        grid.getColumnByKey("lastname").setHeader("Efternamn");
        grid.getColumnByKey("phone").setHeader("Telefon");
        grid.getColumnByKey("username").setHeader("Användarnamn");
        grid.getColumnByKey("role").setHeader("Roll");

        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Sök efternamn...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addUserButton = new Button("Lägg till användare");
        addUserButton.addClickListener(click -> addUser());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addUserButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void addUser() {
        grid.asSingleSelect().clear();
        editUser(new User());
    }

    private void closeEditor() {
        userForm.setUser(null);
        userForm.setVisible(false);
        removeClassName("editing");
    }

    public void editUser(User user) {
        if (user == null) {
            closeEditor();
        } else {
            userForm.setUser(user);
            userForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveUser(StaffUsersForm.SaveEvent event) {
        userController.save(event.getUser());
        updateList();
        closeEditor();
        Notification.show("Person är nu sparad.", 1500,
                Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void deleteUser(StaffUsersForm.DeleteEvent event) {
        userController.delete(event.getUser());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(userController.findAllUser(filterText.getValue(), Role.USER));
    }
}
