package com.PiratesOfTheSiliconValley.LibSys.views.login;

import com.PiratesOfTheSiliconValley.LibSys.security.AuthService;
import com.PiratesOfTheSiliconValley.LibSys.views.publicpages.MainPage;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "login")
@PageTitle("Logga in")
public class LoginView extends VerticalLayout {

    public LoginView(AuthService authService) {
        setId("login-view");

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        TextField username = new TextField("Användarnamn");
        PasswordField password = new PasswordField("Lösenord");
        Button loginButton = new Button("Logga in", 
                                        event -> {
                                            try {
                                                authService.authenticate(username.getValue(), 
                                                                            password.getValue());
                                                UI.getCurrent().navigate(MainPage.class);
                                            } catch (AuthService.AuthException e) {
                                                Notification.show("Fel användarnamn och/eller lösenord.");
                                            }});

        loginButton.addClickShortcut(Key.ENTER);

        add(new H1("Jisho"), username, password, loginButton);
    }
}


