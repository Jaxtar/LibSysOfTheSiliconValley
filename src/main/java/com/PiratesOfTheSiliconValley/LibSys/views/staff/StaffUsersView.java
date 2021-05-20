package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.UserController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/staff/users", layout = Navbar.class)
@PageTitle("Users")
@CssImport("./views/staffview/staffcommon.css")
public class StaffUsersView extends VerticalLayout {

    private UserController userController;
    private StaffUsersForm userForm;

    private Grid<User> grid = new Grid<>(User.class);
    private TextField filterText = new TextField();

    public StaffUsersView(UserController userController) {
        this.userController = userController;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        userForm = new StaffUsersForm();
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

        grid.asSingleSelect().addValueChangeListener(event ->
                editUser(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by lastname...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addUserButton = new Button("Add User");
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