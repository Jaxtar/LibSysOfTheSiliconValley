package com.PiratesOfTheSiliconValley.LibSys.security;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.UserRepository;
import com.vaadin.flow.server.VaadinSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Checks if the passward and username are correct, if so, add a user attribute to the browsing session
    public void authenticate(String username, String password) throws AuthException {
        User user = userRepository.getByUsername(username);

        if (user != null && user.checkPassword(password)){
            VaadinSession.getCurrent().setAttribute(User.class, user);
        } else {
            throw new AuthException();
        }
    }

    public class AuthException extends Exception {

    }

}
