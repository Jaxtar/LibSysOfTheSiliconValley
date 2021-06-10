package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.LoanCardController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan_Card;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/staff/loancard", layout = Navbar.class)
@PageTitle("Lånekort")
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

        grid.getColumnByKey("card_id").setHeader("Kort ID");
        grid.getColumnByKey("status").setHeader("Status");
        grid.getColumnByKey("reason").setHeader("Orsak");

        grid.addComponentColumn(loanCard -> {
            Button enable = new Button("Aktivera");

            Loan_Card finalLoanCard = loanCard;
            enable.addClickListener(click ->
                    enableCard(finalLoanCard)
            );
            loanCard = new Loan_Card();

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

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Sök på orsak...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    //Enables the loan card
    private void enableCard(Loan_Card loanCard) {
        if (loanCard != null && loanCard.getStatus().equals(Loan_Card.Status.DISABLED)) {
            loanCard.setStatus(Loan_Card.Status.ENABLED);
            loanCard.setReason(null);
            Notification.show("Kort " + loanCard.getCard_id() + " är " + loanCard.getStatus(), 
                                1500, 
                                Position.MIDDLE);
            loanCardController.save(loanCard);
            updateList();
        } else {
            Notification.show("Något gick fel! Försök igen.");
            System.out.println("Error (enableCard)");
            System.out.println("LoanCard Status: " + loanCard);
            System.out.println("Reason DBValue: " + loanCard.getReason());
        }
    }

    //Disables the loan card
    private void disableCard(Loan_Card loanCard) {
        RadioButtonGroup<Loan_Card.Reason> reasonRadio = new RadioButtonGroup<>();
        reasonRadio.setItems(Loan_Card.Reason.values());
        Button chooseButton = new Button("Välj");
        Button cancelButton = new Button("Avbryt");
        Notification notification = new Notification(reasonRadio, chooseButton, cancelButton);
        notification.open();

        //Checks the loan card's status and potentially changes it
        chooseButton.addClickListener(event -> {
            if (loanCard != null && loanCard.getStatus().equals(Loan_Card.Status.ENABLED) && reasonRadio.getValue() != null) {
                loanCard.setReason(reasonRadio.getValue());
                loanCard.setStatus(Loan_Card.Status.DISABLED);
                loanCardController.save(loanCard);
                updateList();
                Notification.show("Kort " + loanCard.getCard_id() + " är " + loanCard.getStatus(),
                                    1500,
                                    Position.MIDDLE);
                notification.close();
            } else {
                Notification.show("Något gick fel! Försök igen.",
                                    1500,
                                    Position.MIDDLE);
                System.out.println("Error (disableCard)");
                System.out.println("LoanCard Status: " + loanCard);
                System.out.println("ReasonRadioValue: " + reasonRadio.getValue());
                System.out.println("Reason DBValue: " + loanCard.getReason());
            }
        });

        cancelButton.addClickListener(event -> notification.close());
        notification.setPosition(Notification.Position.MIDDLE);
    }

    private void updateList() {
        grid.setItems(loanCardController.findAll(filterText.getValue()));
    }
}