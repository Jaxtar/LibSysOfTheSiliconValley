package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Decommissioned;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.DecomissionedRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class DecomissionedController {
    private static final Logger LOGGER = Logger.getLogger(DecomissionedController.class.getName());
    private DecomissionedRepository decomissionedRepository;
    LocalDateTime date = LocalDateTime.now();

    public DecomissionedController(DecomissionedRepository decomissionedRepository) {this.decomissionedRepository = decomissionedRepository;}

    public List<Decommissioned> findAll() {
        return decomissionedRepository.findAll();
    }

    public void delete(Decommissioned decommissioned) {
        decomissionedRepository.delete(decommissioned);
    }

    public void save(Decommissioned decommissioned) {
        if (decommissioned == null) {
            LOGGER.log(Level.SEVERE,
                    "Bok decomissioned is null. Are you sure you have connected your form to the application?");
            return;
        }
        decommissioned.setDate_removed(date);
        decomissionedRepository.save(decommissioned);
    }
}
