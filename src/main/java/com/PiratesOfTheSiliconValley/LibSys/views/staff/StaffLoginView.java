package com.PiratesOfTheSiliconValley.LibSys.views.staff;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route("/login")
@PageTitle("Login | Jisho")
public class StaffLoginView extends VerticalLayout implements BeforeEnterObserver {
        private LoginForm login = new LoginForm();

        public StaffLoginView(){
            addClassName("login-view");
            setSizeFull();
            setAlignItems(Alignment.CENTER);
            setJustifyContentMode(JustifyContentMode.CENTER);

            login.setAction("login");

            add(new H1("Jisho"), login);
        }

        @Override
        public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
            // inform the user about an authentication error
            if(beforeEnterEvent.getLocation()
                    .getQueryParameters()
                    .getParameters()
                    .containsKey("error")) {
                login.setError(true);
            }
        }
    }

