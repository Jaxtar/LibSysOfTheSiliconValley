package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.DecommissionedController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Decommissioned;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/staff/decommissioned", layout = Navbar.class)
@PageTitle("StaffDecommissioned")
@CssImport("./views/staffview/staffcommon.css")
public class StaffDecommissionedView extends VerticalLayout {

    private DecommissionedController decommissionedController;

    private Grid<Decommissioned> grid = new Grid<>(Decommissioned.class);
    private TextField filterText = new TextField();

    public StaffDecommissionedView(DecommissionedController decommissionedController){
        this.decommissionedController = decommissionedController;
        addClassName("list-view");
        setSizeFull();
        configureGrid();

        add(getToolbar(),grid);
        updateList();
    }

    private void configureGrid() {
        addClassName("decommissioned-grid");
        grid.setSizeFull();
        grid.setColumns("book_isbn", "title", "classification", "condition", "date_added", "date_removed", "reason");
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
    private void updateList() {
        grid.setItems(decommissionedController.findAll(filterText.getValue()));
    }
}