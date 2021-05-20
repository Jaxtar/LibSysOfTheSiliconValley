package com.PiratesOfTheSiliconValley.LibSys.views.login;

import com.PiratesOfTheSiliconValley.LibSys.security.AuthService;
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
@PageTitle("Login")
public class LoginView extends VerticalLayout {
        public LoginView(AuthService authService) {
            setId("login-view");
            TextField username = new TextField("Username");
            PasswordField password = new PasswordField("Password");
            add(
                    new H1("Jisho"),
                    username,
                    password,
                    new Button("Login", event -> {

                        try {
                            authService.authenticate(username.getValue()/*, password.getValue()*/);
                            UI.getCurrent().navigate("loggedinmain");
                        } catch (AuthService.AuthException e) {
                            Notification.show("Wrong username or password.");
                        }
                    })
            );
        }
}


