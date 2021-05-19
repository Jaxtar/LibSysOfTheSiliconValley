package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Person;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.PersonRepository;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class PersonController {

    private static final Logger LOGGER = Logger.getLogger(PersonController.class.getName());
    private PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }


    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public List<Person> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return personRepository.findAll();
        } else {
            return personRepository.findByLastnameContainsIgnoreCase(stringFilter);
        }
    }

    // public long count() { return bookRepository.count(); }

    public void delete(Person person) {
        personRepository.delete(person);
    }

    public void save(Person person) {
        if (person == null) {
            LOGGER.log(Level.SEVERE,
                    "Person is null. Are you sure you have connected your form to the application?");
            return;
        }
        personRepository.save(person);
    }

}