package com.example.login.controller;

import com.example.login.dto.MessageResponse;
import com.example.login.model.Staff;
import com.example.login.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/staff")
@RequiredArgsConstructor
@Slf4j
public class StaffController {
    
    private final StaffService staffService;
    
    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Staff>> getAllStaff() {
        log.info("Getting all staff members");
        return ResponseEntity.ok(staffService.getAllStaff());
    }
    
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> getStaffById(@PathVariable Long id) {
        log.info("Getting staff with ID: {}", id);
        return staffService.getStaffById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createStaff(@RequestBody Staff staff) {
        log.info("Creating new staff member");
        try {
            Staff createdStaff = staffService.createStaff(staff);
            return ResponseEntity.ok(createdStaff);
        } catch (Exception e) {
            log.error("Error creating staff: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error creating staff: " + e.getMessage()));
        }
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateStaff(@PathVariable Long id, @RequestBody Staff staffDetails) {
        log.info("Updating staff with ID: {}", id);
        return staffService.updateStaff(id, staffDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStaff(@PathVariable Long id) {
        log.info("Deleting staff with ID: {}", id);
        try {
            staffService.deleteStaff(id);
            return ResponseEntity.ok(new MessageResponse("Staff deleted successfully"));
        } catch (Exception e) {
            log.error("Error deleting staff: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
}
