package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import java.time.LocalDateTime;

import com.PiratesOfTheSiliconValley.LibSys.backend.controller.InventoryController;
import com.PiratesOfTheSiliconValley.LibSys.backend.controller.LoanController;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Loan_Card;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.InventoryRepository;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.LoanCardRepository;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.Navbar;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="/staff/loan", layout = Navbar.class)
@PageTitle("Låna")
@CssImport("./views/staffview/staffcommon.css")
public class StaffLoanPage extends VerticalLayout{
    private LoanController loanController;
    private InventoryRepository inventoryRepo;
    private LoanCardRepository loanCardRepo;
    private InventoryController invCont;

    IntegerField bookId = new IntegerField("Bokens identifikationsnummer");
    LocalDateTime loanDate = LocalDateTime.now();
    IntegerField cardId = new IntegerField("Lånekortets identifikationsnummer");

    Binder<Loan> binder = new BeanValidationBinder<>(Loan.class);

    Button loanButton = new Button("Låna");

    public StaffLoanPage(LoanController loanController, InventoryController invCont, InventoryRepository inventoryRepo, LoanCardRepository loanCardRepo){
        this.loanController = loanController;
        this.inventoryRepo = inventoryRepo;
        this.loanCardRepo = loanCardRepo;
        this.invCont = invCont;

        addClassName("loanPage");
        binder.bindInstanceFields(this);
        binder.addValueChangeListener(e -> loanButton.setEnabled(binder.isValid()));

        bookId.setMinWidth("30em");
        cardId.setMinWidth("30em");

        loanButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        loanButton.addClickListener(e -> save());
        loanButton.setEnabled(false);

        add(bookId, cardId, loanButton);
        setAlignItems(Alignment.CENTER);
    }

    private void save(){
        //Är kortet avstängt?
        if(loanCardRepo.findByCardId(cardId.getValue()) != null
            && loanCardRepo.findByCardId(cardId.getValue()).get(0).getStatus() == Loan_Card.Status.DISABLED){
            Notification.show("Lånekortet är spärrat",
                                1500,
                                Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
        //Eller är den inte inne?
        } else if (inventoryRepo.findByBookID(bookId.getValue()) != null
                    && inventoryRepo.findByBookID(bookId.getValue()).get(0).getStatus() != Inventory.Status.INNE){
            Notification.show("Boken är inte inne, om du ändå håller i den kontakta biblotikarien",
                                1500,
                                Notification.Position.MIDDLE).addThemeVariants(NotificationVariant.LUMO_ERROR);
        } else{
            Loan loan = new Loan();
            loan.setBookId(bookId.getValue());
            loan.setLoanDate(LocalDateTime.now());
            loan.setCardId(cardId.getValue());

            loanController.save(loan);
            Inventory inv = inventoryRepo.findByBookID(bookId.getValue()).get(0);
            inv.setStatus(Inventory.Status.UTLÅNAD);
            invCont.save(inv);
        
            Notification.show("Lånet är registrerat.", 
                                1500,
                                Notification.Position.MIDDLE ).addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }
    }
}
