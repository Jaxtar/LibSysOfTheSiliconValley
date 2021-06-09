package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
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

    //Intializes the controller
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //Gets a user by their name
    public User getByUsername(String filterName){
        return userRepository.getByUsername(filterName);
    }

    //Finds all the users
    public List<User> findAll() {
        return userRepository.findAll();
    }

    //Finds all the users given a role and search term
    public List<User> findAllUser(String stringFilter, Role role) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRepository.findByRole(role);
        } else {
            return userRepository.findByLastnameContainsIgnoreCaseAndRole(stringFilter, role);
        }
    }

    //Finds all the users with the given search term
    public List<User> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return userRepository.findAll();
        } else {
            return userRepository.findByLastnameContainsIgnoreCase(stringFilter);
        }
    }

    //Removes a user
    public void delete(User user) {
        userRepository.delete(user);
    }

    //Saves a user
    public void save(User user) {
        if (user == null) {
            LOGGER.log(Level.SEVERE,
                    "User is null. Are you sure you have connected your form to the application?");
            return;
        } else if (user.getPasswordHash() == null) {
            LOGGER.log(Level.SEVERE,
                    "Hash is Null.");
            return;
        } else if (user.getRole() == null) {
            LOGGER.log(Level.SEVERE,
                    "Role is Null.");
            return;
        }
        userRepository.save(user);
    }
}