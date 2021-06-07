package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import java.time.LocalDateTime;
import java.util.List;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.InventoryController;
import com.PiratesOfTheSiliconValley.LibSys.backend.controller.LoanController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.InventoryRepository;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.LoanRepository;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/staff/return", layout = Navbar.class)
@PageTitle("Returnera")
@CssImport("./views/staffview/staffcommon.css")
public class StaffReturnPage extends VerticalLayout {
    private LoanRepository loanRepo;
    private InventoryRepository inventoryRepo;
    private InventoryController invCont;
    private LoanController loanCont;

    IntegerField bookID = new IntegerField("Bokens identifikationsnummer");

    Button returnButton = new Button("Lämna tillbaka");

    public StaffReturnPage(LoanRepository loan, LoanController loanCont, InventoryRepository invRepo, InventoryController invCont){
        this.loanRepo = loan;
        this.inventoryRepo = invRepo;
        this.invCont = invCont;
        this.loanCont = loanCont;

        addClassName("returSida");

        returnButton.addClickListener(e -> returnCheck());

        add(bookID, returnButton);
    }

    private void returnCheck() {
        if(bookID.getValue() != null){
            Inventory inv = inventoryRepo.findByBookID(bookID.getValue()).get(0);
            inv.setStatus(Inventory.Status.INNE);
            invCont.save(inv);

            List<Loan> temp = loanRepo.findByBookId(bookID.getValue());
            Loan loan = temp.get(temp.size()-1);
            loan.setReturnDate(LocalDateTime.now());
            loanCont.save(loan);
            
            Notification.show("Boken är återlämnad", 1500, Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        } else{
            Notification.show("Inget ID skrivet", 1500, Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);;
        }
    }
}