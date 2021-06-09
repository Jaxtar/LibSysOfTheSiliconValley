package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.LoanController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;
import com.vaadin.flow.component.UI;
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

@Route(value = "/staff/userreportview", layout = Navbar.class)
@PageTitle("Användare - Lånat")
@CssImport("./views/staffview/staffcommon.css")
public class StaffUserReportView extends VerticalLayout {

    private LoanController loanController;

    private Grid<Loan> grid = new Grid<>(Loan.class);

    private TextField filterText = new TextField();


    User user = new User();

    public StaffUserReportView(LoanController loanController) {
        this.loanController = loanController;

        addClassName("list-view");
        setSizeFull();
        configureGrid();

        Div content = new Div(grid);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolbar(), content);
        updateList();
    }

    private void configureGrid() {
        addClassName("user-grid");
        grid.setSizeFull();
        grid.setColumns("cardId", "bookId");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.getColumnByKey("cardId").setHeader("Kort-ID");
        grid.getColumnByKey("bookId").setHeader("Bok-ID");

    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Sök bok...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());
        filterText.setReadOnly(true);
        filterText.setValue("Sök ej tillgängligt");


        Button returnToSearchButton = new Button("Rapport på annan användare");
        returnToSearchButton.addClickListener(click -> {
                    UI.getCurrent()
                            .navigate(StaffUserReportSearch.class);
                });

        HorizontalLayout toolbar = new HorizontalLayout(filterText, returnToSearchButton);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    void updateList() {
       //grid.setItems(loanController.findByCardId(user.getCard_id()));
        grid.setItems(loanController.findAll());
    }


    public void receiveUser(User user) {
       this.user = user;
    }

}
