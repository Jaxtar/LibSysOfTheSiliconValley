package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.LoanCardController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan_Card;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/staff/loancard", layout = Navbar.class)
@PageTitle("LoanCard")
@CssImport("./views/staffview/staffcommon.css")

public class StaffLoanCardView extends VerticalLayout {

    private LoanCardController loanCardController;

    private Grid<Loan_Card> grid = new Grid<>(Loan_Card.class);
    private TextField filterText = new TextField();

    public StaffLoanCardView(LoanCardController loanCardController) {
        this.loanCardController = loanCardController;
        addClassName("list-view");
        setSizeFull();
        configureGrid();


        Div content = new Div(grid);
        content.addClassName("content");
        content.setSizeFull();

        /*
        add(filterInteger, grid);
        addClassName("content");
        setSizeFull();
         */

        add(getToolbar(), content);
        updateList();
    }

    private void configureGrid() {
        addClassName("loancard-grid");
        grid.setSizeFull();
        grid.setColumns("card_id", "status", "reason");

        //grid.asSingleSelect().addValueChangeListener(event -> editLoan_Card(event.getValue()));
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Filter by Reason...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }


    private void updateList() {
        grid.setItems(loanCardController.findAll(filterText.getValue()));
    }


}
