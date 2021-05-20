package com.PiratesOfTheSiliconValley.LibSys.security;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.UserRepository;
import com.PiratesOfTheSiliconValley.LibSys.views.login.LoggedInLayout;
import com.PiratesOfTheSiliconValley.LibSys.views.logout.LogoutView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffBookView;
import com.PiratesOfTheSiliconValley.LibSys.views.login.LoggedInMain;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.StaffPersonView;
import com.PiratesOfTheSiliconValley.LibSys.views.staff.UsersView;
import com.PiratesOfTheSiliconValley.LibSys.views.user.UserAccount;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class AuthService {
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public record AuthorizedRoute(String path, String name, Class<? extends Component> view) {
    }

    public void authenticate(String username/*, String password*/) throws AuthException {
        User user = userRepository.getByUsername(username);

        System.out.println("ErrorLogg/What THe Fakk is going on");
        System.out.println("User: " + user.getUsername());
        System.out.println("Role: " + user.getRole());
        // System.out.println("Password: " + password);
        System.out.println("Hash: " + user.getPasswordHash());
       // System.out.println("Salt: " + user.getPasswordSalt());
       // System.out.println("Check Password: " + user.checkPassword(password));

        if (user != null /*&& user.checkPassword(password)*/){
            VaadinSession.getCurrent().setAttribute(User.class, user);
            createRoutes(user.getRole());
        } else {
            throw new AuthException();
        }
    }

    public class AuthException extends Exception {

    }

    private void createRoutes(Role role) {
        getAuthorizedRoutes(role).stream()
                .forEach(route ->
                        RouteConfiguration.forSessionScope().setRoute(
                                route.path, route.view, LoggedInLayout.class));
    }

    public List<AuthorizedRoute> getAuthorizedRoutes(Role role) {
        ArrayList<AuthorizedRoute> routes = new ArrayList<AuthorizedRoute>();

        if (role.equals(Role.USER)) {
            routes.add(new AuthorizedRoute("loggedinmain", "Main", LoggedInMain.class));
            routes.add(new AuthorizedRoute("useraccount", "Account", UserAccount.class));
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
        } else if (role.equals(Role.ADMIN)) {
            routes.add(new AuthorizedRoute("loggedinmain", "Main", LoggedInMain.class));
            routes.add(new AuthorizedRoute("staffbook", "Books", StaffBookView.class));
            routes.add(new AuthorizedRoute("staffperson", "Person List", StaffPersonView.class));
            routes.add(new AuthorizedRoute("staffuser", "User List", UsersView.class));
            routes.add(new AuthorizedRoute("logout", "Logout", LogoutView.class));
        }
        return routes;
    }

}
