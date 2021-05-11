package com.PiratesOfTheSiliconValley.LibSys.security;

import com.PiratesOfTheSiliconValley.LibSys.views.AboutUsView;
import com.PiratesOfTheSiliconValley.LibSys.views.MainPage;
import com.PiratesOfTheSiliconValley.LibSys.views.OpenHoursView;
import com.PiratesOfTheSiliconValley.LibSys.views.SeminarView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffBookView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffLoginView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffMainView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;
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
        if ((StaffBookView.class.equals(event.getNavigationTarget())
                || StaffMainView.class.equals(event.getNavigationTarget()))
                && !SecurityUtils.isUserLoggedIn()) {
            event.rerouteTo(StaffLoginView.class);
        }
    }
}