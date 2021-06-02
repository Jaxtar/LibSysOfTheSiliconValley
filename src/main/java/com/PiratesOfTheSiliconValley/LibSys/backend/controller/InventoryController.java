package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.InventoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class InventoryController {
    private static final Logger LOGGER = Logger.getLogger(InventoryController.class.getName());
    private InventoryRepository inventoryRepository;
    LocalDateTime date = LocalDateTime.now();

    public InventoryController(InventoryRepository inventoryRepository) {this.inventoryRepository = inventoryRepository;}

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    public List<Inventory> findAll(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return inventoryRepository.findAll();
        } else {
            return inventoryRepository.findByIsbn(stringFilter);
        }
    }

    public void delete(Inventory inventory) {
        inventoryRepository.delete(inventory);
    }

    public void save(Inventory inventory) {
        if (inventory == null) {
            LOGGER.log(Level.SEVERE,
                    "Inventory is null. Are you sure you have connected your form to the application?");
            return;
        }
        inventory.setDate_added(date);
        inventoryRepository.save(inventory);
    }
}
