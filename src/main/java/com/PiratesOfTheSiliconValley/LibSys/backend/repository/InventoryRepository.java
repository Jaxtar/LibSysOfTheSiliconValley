package com.PiratesOfTheSiliconValley.LibSys.backend.repository;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Inventory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    List<Inventory> findAll();
    List<Inventory> findByTitleContainsIgnoreCase(String searchTerm);
    List<Inventory> findByBookID(Integer bookID);
}
