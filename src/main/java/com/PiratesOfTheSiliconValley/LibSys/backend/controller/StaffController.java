package com.PiratesOfTheSiliconValley.LibSys.backend.controller;

import com.PiratesOfTheSiliconValley.LibSys.backend.model.Staff;
import com.PiratesOfTheSiliconValley.LibSys.backend.repository.StaffRepository;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class StaffController {
    private static final Logger LOGGER = Logger.getLogger(StaffController.class.getName());
    private StaffRepository staffRepository;
    private Staff staff;

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
