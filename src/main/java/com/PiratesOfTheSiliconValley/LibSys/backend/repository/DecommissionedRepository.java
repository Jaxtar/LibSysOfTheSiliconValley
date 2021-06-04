package com.PiratesOfTheSiliconValley.LibSys.backend.repository;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Decommissioned;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DecommissionedRepository extends JpaRepository<Decommissioned, Integer> {

    List<Decommissioned> findAll();
    List<Decommissioned> findByTitleContainsIgnoreCase(String searchTerm);
}

