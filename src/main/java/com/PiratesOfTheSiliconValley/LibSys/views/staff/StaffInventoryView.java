package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.InventoryController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;
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

@Route(value = "/staff/inventory", layout = Navbar.class)
@PageTitle("StaffInventory")
@CssImport("./views/staffview/staffcommon.css")
public class StaffInventoryView  extends VerticalLayout {

    private InventoryController inventoryController;
    private StaffInventoryForm staffInventoryForm;

    private Grid<Inventory> grid = new Grid<>(Inventory.class);
    private TextField filterText = new TextField();

    public StaffInventoryView(InventoryController inventoryController){
        this.inventoryController = inventoryController;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        staffInventoryForm = new StaffInventoryForm();
        staffInventoryForm.addListener(StaffInventoryForm.SaveEvent.class, this::saveInventory);
        staffInventoryForm.addListener(StaffInventoryForm.CloseEvent.class, e -> closeEditor());
        staffInventoryForm.setMinWidth("20em");

        Div content = new Div(grid, staffInventoryForm);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
        closeEditor();
    }

    private void configureGrid() {
        addClassName("inventory-grid");
        grid.setSizeFull();
        grid.setColumns("isbn", "title", "classification", "condition", "status", "date_added");

        grid.asSingleSelect().addValueChangeListener(event ->
                editInventory(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by title...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void closeEditor() {
        staffInventoryForm.setInventory(null);
        staffInventoryForm.setVisible(false);
        removeClassName("editing");
    }

    public void editInventory(Inventory inventory) {
        if (inventory == null) {
            closeEditor();
        } else {
            staffInventoryForm.setInventory(inventory);
            staffInventoryForm.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveInventory(StaffInventoryForm.SaveEvent event) {
        inventoryController.save(event.getInventory());
        updateList();
        closeEditor();
        Notification.show("Boken är nu sparad", 1500,
                Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void updateList() {
        grid.setItems(inventoryController.findAll(filterText.getValue()));
    }
}