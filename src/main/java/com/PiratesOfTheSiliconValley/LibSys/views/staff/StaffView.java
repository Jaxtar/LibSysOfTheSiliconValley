package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.StaffController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Staff;
import com.PiratesOfTheSiliconValley.LibSys.editor.StaffForm;
import com.vaadin.flow.component.button.Button;
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

@Route(value = "/staff/info", layout = StaffLayout.class)
@PageTitle("Biblioteket Jisho")
public class StaffView extends VerticalLayout {

    private StaffController staffController;
    private StaffForm staffForm;

    private Grid<Staff> grid = new Grid<>(Staff.class);
    private TextField filterText = new TextField();

    public StaffView(StaffController staffController){
        this.staffController = staffController;
        addClassName("staff-view");
        setSizeFull();
        configureGrid();

        staffForm = new StaffForm();
        staffForm.addListener(StaffForm.SaveEvent.class, this::saveStaff);
        staffForm.addListener(StaffForm.DeleteEvent.class, this::deleteStaff);
        staffForm.addListener(StaffForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, staffForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        addClassName("staff-grid");
        grid.setSizeFull();
        grid.setColumns("firstname", "lastname", "username", "email", "occupation");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event ->
                editStaff(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by lastname...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addStaffButton = new Button("Lägg till ny anställd");
        addStaffButton.addClickListener(click -> addStaff());

        HorizontalLayout toolbar = new HorizontalLayout(filterText, addStaffButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void addStaff() {
            grid.asSingleSelect().clear();
            editStaff(new Staff());
    }

    private void closeEditor() {
        staffForm.setStaff(null);
        staffForm.setVisible(false);
        removeClassName("editing");
    }

    public void editStaff(Staff staff) {
        if (staff == null) {
            closeEditor();
        }else {
            staffForm.setStaff(staff);
            staffForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveStaff(StaffForm.SaveEvent event) {
        staffController.save(event.getStaff());
        updateList();
        closeEditor();
        Notification.show("Anställd är nu sparad.", 1500,
                Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void deleteStaff(StaffForm.DeleteEvent event) {
        staffController.delete(event.getStaff());
        updateList();
        closeEditor();
    }

    private void updateList() {
        grid.setItems(staffController.findAll(filterText.getValue()));
    }
}


