package com.PiratesOfTheSiliconValley.LibSys.security;


import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.views.login.LoginView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffBookView;
import com.PiratesOfTheSiliconValley.LibSys.views.admin.AdminUsersView;

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

    private void authenticateNavigation(BeforeEnterEvent event) {
        User user = VaadinSession.getCurrent().getAttribute(User.class);

        if ((StaffBookView.class.equals(event.getNavigationTarget())
                || AdminUsersView.class.equals(event.getNavigationTarget())
                )
                && (user == null || user.getRole().equals(Role.USER))) {
            event.rerouteTo(LoginView.class);
        }
    }
}