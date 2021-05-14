package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Book;
import com.PiratesOfTheSiliconValley.LibSys.backend.model.Staff;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.StaffRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StaffController {
    private static final Logger LOGGER = Logger.getLogger(StaffController.class.getName());
    private StaffRepository staffRepository;

    public StaffController(StaffRepository staffRepository){
        this.staffRepository = staffRepository;
    }

    public List<Staff> findAll(){
        return staffRepository.findAll();
    }

    public List<Staff> findAll(String stringFilter){
        if (stringFilter == null || stringFilter.isEmpty()){
            return staffRepository.findAll();
        }else {
            return staffRepository.findByLastnameContainsIgnoreCase(stringFilter);
        }
    }
    public void delete(Staff staff) {
        staffRepository.delete(staff);
    }

    public void save(Staff staff) {
        if (staff == null) {
            LOGGER.log(Level.SEVERE,
                    "Staff is null. Are you sure you have connected your form to the application?");
            return;
        }
        staffRepository.save(staff);
    }

}
