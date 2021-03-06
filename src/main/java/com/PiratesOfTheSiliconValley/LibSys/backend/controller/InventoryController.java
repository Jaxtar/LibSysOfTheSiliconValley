package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.InventoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InventoryController {
    private static final Logger LOGGER = Logger.getLogger(InventoryController.class.getName());
    private InventoryRepository inventoryRepository;

    //Initializes the controller
    public InventoryController(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    //Finds all books in the inventory
    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    //Finds all books in the inventory with the given search term
    public List<Inventory> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return inventoryRepository.findAll();
        } else {
            return inventoryRepository.findByTitleContainsIgnoreCase(stringFilter);
        }
    }

    //Removes a book from the inventory
    public void delete(Inventory inventory) {
        inventoryRepository.delete(inventory);
    }

    //Adds a book to the inventory
    public void save(Inventory inventory) {
        if (inventory == null) {
            LOGGER.log(Level.SEVERE,
                    "Inventory is null. Are you sure you have connected your form to the application?");
            return;
        }
        inventoryRepository.save(inventory);
    }
}
