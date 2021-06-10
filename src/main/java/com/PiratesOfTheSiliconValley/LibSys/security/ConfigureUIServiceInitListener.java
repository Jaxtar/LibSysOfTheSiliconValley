package com.PiratesOfTheSiliconValley.LibSys.security;


import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.views.login.LoginView;
import com.PiratesOfTheSiliconValley.LibSys.views.admin.AdminUsersView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffBookView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffDecommissionedView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffInventoryView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffLoanCardView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffLoanPage;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffReturnPage;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffUsersView;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
import com.vaadin.flow.server.VaadinSession;

import org.springframework.stereotype.Component;

@Component
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.getSource().addUIInitListener(uiEvent -> {
            final UI ui = uiEvent.getUI();
            ui.addBeforeEnterListener(this::authenticateNavigation);
        });
    }

    /**
     * Redirects the user if they attempt to access a staff site while not logged in on a staff account
     */
    private void authenticateNavigation(BeforeEnterEvent event) {
        User user = VaadinSession.getCurrent().getAttribute(User.class);

        if ((StaffBookView.class.equals(event.getNavigationTarget())
                || AdminUsersView.class.equals(event.getNavigationTarget())
                || StaffInventoryView.class.equals(event.getNavigationTarget())
                || StaffUsersView.class.equals(event.getNavigationTarget())
                || StaffDecommissionedView.class.equals(event.getNavigationTarget())
                || StaffLoanCardView.class.equals(event.getNavigationTarget())
                || StaffLoanPage.class.equals(event.getNavigationTarget())
                || StaffReturnPage.class.equals(event.getNavigationTarget())
                || StaffUserReportSearch.class.equals(event.getNavigationTarget())
                || StaffUserReportView.class.equals(event.getNavigationTarget())
        )
                && (user == null || user.getRole().equals(Role.USER))) {
            event.rerouteTo(LoginView.class);
        }
    }
}