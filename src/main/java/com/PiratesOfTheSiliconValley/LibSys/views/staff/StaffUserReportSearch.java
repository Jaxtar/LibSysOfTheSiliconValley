package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.LoanController;
import com.PiratesOfTheSiliconValley.LibSys.backend.controller.UserController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "/staff/userreportsearch", layout = Navbar.class)
@PageTitle("Användare - Lånat")
@CssImport("./views/staffview/staffcommon.css")
public class StaffUserReportSearch extends VerticalLayout {

    private UserController userController;

    private LoanController loanController;

    private Grid<User> grid = new Grid<>(User.class);

    private Grid<Loan> gridLoan = new Grid<>(Loan.class);

    private TextField filterText = new TextField();

    public StaffUserReportSearch(UserController userController) {
        this.userController = userController;
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
        grid.setColumns("personal_id_number","card_id", "firstname", "lastname");
        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.getColumnByKey("personal_id_number").setHeader("Personnummer");
        grid.getColumnByKey("card_id").setHeader("Kort-ID");
        grid.getColumnByKey("firstname").setHeader("Förnamn");
        grid.getColumnByKey("lastname").setHeader("Efternamn");

        grid.asSingleSelect().addValueChangeListener(event -> showReportChoice(event.getValue()));
    }

    private void showReportChoice(User user){
        Span reportQuestion = new Span("Visa rapport på " +
                user.getFirstname() + " " + user.getLastname() + "?");
        Button confirmButton = new Button("Ja");
        Button cancelButton = new Button("Avbryt");
        HorizontalLayout buttons = new HorizontalLayout(confirmButton, cancelButton);


        Notification notification = new Notification(reportQuestion, buttons);
        notification.open();
        notification.setPosition(Notification.Position.MIDDLE);

        confirmButton.addClickListener(click -> {
            UI.getCurrent()
                    .navigate(StaffUserReportView.class);
            notification.close();
        });
        cancelButton.addClickListener(click -> notification.close());
    }

    private HorizontalLayout getToolbar() {
        filterText.setPlaceholder("Sök efternamn...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        HorizontalLayout toolbar = new HorizontalLayout(filterText);
        toolbar.addClassName("toolbar");
        return toolbar;
    }

    private void updateList() {
        grid.setItems(userController.findAllUser(filterText.getValue(), Role.USER));
    }
}
