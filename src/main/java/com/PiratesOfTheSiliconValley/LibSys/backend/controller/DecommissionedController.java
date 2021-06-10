package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Decommissioned;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.DecommissionedRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DecommissionedController {
    private static final Logger LOGGER = Logger.getLogger(DecommissionedController.class.getName());
    private DecommissionedRepository decommissionedRepository;

    //Initializes the controller
    public DecommissionedController(DecommissionedRepository decommissionedRepository) {
        this.decommissionedRepository = decommissionedRepository;
    }

    //Finds all the decommisioned books
    public List<Decommissioned> findAll() {
        return decommissionedRepository.findAll();
    }

    //Finds all the decommisioned books with the given search term
    public List<Decommissioned> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return decommissionedRepository.findAll();
        } else {
            return decommissionedRepository.findByTitleContainsIgnoreCase(stringFilter);
        }
    }

    //Removes the given decommissioned book
    public void delete(Decommissioned decommissioned) {
        decommissionedRepository.delete(decommissioned);
    }

    //Adds the given decommissioned book
    public void save(Decommissioned decommissioned) {
        if (decommissioned == null) {
            LOGGER.log(Level.SEVERE,
                    "Bok decomissioned is null. Are you sure you have connected your form to the application?");
            return;
        }
        decommissionedRepository.save(decommissioned);
    }
}
