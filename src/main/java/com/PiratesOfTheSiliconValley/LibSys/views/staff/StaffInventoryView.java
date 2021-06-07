package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.DecommissionedController;
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
    private DecommissionedController decommissionedController;
    private StaffInventoryForm staffInventoryForm;

    private Grid<Inventory> grid = new Grid<>(Inventory.class);
    private TextField filterText = new TextField();

    public StaffInventoryView(InventoryController inventoryController, DecommissionedController decommissionedController){
        this.inventoryController = inventoryController;
        this.decommissionedController = decommissionedController;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        staffInventoryForm = new StaffInventoryForm(decommissionedController);
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
        grid.setColumns("isbn", "title", "classification", "book_condition", "status", "date_added");

        grid.getColumnByKey("isbn").setHeader("ISBN");
        grid.getColumnByKey("title").setHeader("Titel");
        grid.getColumnByKey("classification").setHeader("Klassificering");
        grid.getColumnByKey("book_condition").setHeader("Skick");
        grid.getColumnByKey("status").setHeader("Status");
        grid.getColumnByKey("date_added").setHeader("Tillagd").setAutoWidth(true);

        grid.asSingleSelect().addValueChangeListener(event ->
                editInventory(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Sök titel...");
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
        Notification.show("Ändringen är nu sparad", 1500,
                Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
    }

    private void updateList() {
        grid.setItems(inventoryController.findAll(filterText.getValue()));
    }
}