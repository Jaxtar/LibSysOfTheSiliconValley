package com.PiratesOfTheSiliconValley.LibSys.backend.repository;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Role;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getByUsername(String username);

    List<User> findByRole(Role role);
    List<User> findAll();
    List<User> findByLastnameContainsIgnoreCase(String searchTerm);
    List<User> findByLastnameContainsIgnoreCaseAndRole(String searchTerm, Role role);
}
