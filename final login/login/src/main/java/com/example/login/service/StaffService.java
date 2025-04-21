package com.example.login.service;

import com.example.login.model.Staff;
import com.example.login.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StaffService {
    
    private final StaffRepository staffRepository;
    
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }
    
    public Optional<Staff> getStaffById(Long id) {
        return staffRepository.findById(id);
    }
    
    @Transactional
    public Staff createStaff(Staff staff) {
      
        if (staffRepository.existsByEmail(staff.getEmail())) {
            log.error("Email already in use: {}", staff.getEmail());
            throw new IllegalArgumentException("Email already in use");
        }
        
        return staffRepository.save(staff);
    }
    
    @Transactional
    public Optional<Staff> updateStaff(Long id, Staff staffDetails) {
        return staffRepository.findById(id)
                .map(existingStaff -> {
                   
                    if (!existingStaff.getEmail().equals(staffDetails.getEmail()) && 
                            staffRepository.existsByEmail(staffDetails.getEmail())) {
                        log.error("Email already in use: {}", staffDetails.getEmail());
                        throw new IllegalArgumentException("Email already in use");
                    }
                    
                    existingStaff.setName(staffDetails.getName());
                    existingStaff.setEmail(staffDetails.getEmail());
                    existingStaff.setPhone(staffDetails.getPhone());
                    existingStaff.setDepartment(staffDetails.getDepartment());
                    existingStaff.setPosition(staffDetails.getPosition());
                    
                    return staffRepository.save(existingStaff);
                });
    }
    
    @Transactional
    public void deleteStaff(Long id) {
        if (!staffRepository.existsById(id)) {
            log.error("Staff not found with id: {}", id);
            throw new IllegalArgumentException("Staff not found with id: " + id);
        }
        
        staffRepository.deleteById(id);
    }
}