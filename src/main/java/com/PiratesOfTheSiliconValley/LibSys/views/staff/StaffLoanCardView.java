package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.LoanCardController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan_Card;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
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

        add(getToolbar(), content);
        updateList();
    }

    private void configureGrid() {
        addClassName("loancard-grid");
        grid.setSizeFull();
        grid.setColumns("card_id", "status", "reason");

        grid.addComponentColumn(loanCard -> {
            Button enable = new Button("Aktivera");

            Loan_Card finalLoanCard = loanCard;
            enable.addClickListener(click ->
                    enableCard(finalLoanCard)
            );
            loanCard = new Loan_Card();
            System.out.println("loanCard: " + loanCard);

            return enable;
        });

        grid.addComponentColumn(loanCard -> {
            Button disable = new Button("Spärra");

            Loan_Card finalLoanCard = loanCard;
            disable.addClickListener(click ->
                    disableCard(finalLoanCard)
            );
            loanCard = new Loan_Card();

            return disable;
        });
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

    private void enableCard(Loan_Card loanCard) {

        if (loanCard != null || loanCard.getStatus().equals(Loan_Card.Status.DISABLED)) {
            loanCard.setStatus(Loan_Card.Status.ENABLED);
        } else {
            System.out.println("Error (enableCard): " + loanCard);
        }

        Notification.show("Kort " + loanCard.getCard_id() + " är nu " + loanCard.getStatus());

        loanCardController.save(loanCard);
        updateList();
    }

    private void disableCard(Loan_Card loanCard) {

        if (loanCard != null || loanCard.getStatus().equals(Loan_Card.Status.ENABLED)) {
            loanCard.setStatus(Loan_Card.Status.DISABLED);
        } else {
            System.out.println("Error (disableCard): " + loanCard);
        }

        Notification.show("Kort " + loanCard.getCard_id() + " är nu " + loanCard.getStatus());

        loanCardController.save(loanCard);
        updateList();
    }

    private void updateList() {
        grid.setItems(loanCardController.findAll(filterText.getValue()));
    }

}

