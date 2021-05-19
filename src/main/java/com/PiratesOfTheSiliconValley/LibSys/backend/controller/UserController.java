package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class UserController {

    private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getByUsername(String filterName){
        return userRepository.getByUsername(filterName);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<User> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.findByUsernameContainsIgnoreCase(stringFilter);
        }
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public void save(User user) {
        if (user == null) {
            LOGGER.log(Level.SEVERE,
                    "User is null. Are you sure you have connected your form to the application?");
            return;
        } else if (user.getPasswordHash() == null) {
            LOGGER.log(Level.SEVERE,
                    "Hash is Null.");
            return;
        }
        /*
        else if (user.getPasswordSalt() == null){

            LOGGER.log(Level.SEVERE,
                    "Salt is Null.");
            return;
        }*/
        userRepository.save(user);
    }
}