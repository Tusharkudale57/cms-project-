package com.example.login.repository;

import com.example.login.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    
    Optional<Student> findByEmail(String email);
    
    Optional<Student> findByRollNumber(String rollNumber);
    
    List<Student> findByStaffId(Long staffId);
    
    Boolean existsByEmail(String email);
    
    Boolean existsByRollNumber(String rollNumber);
}
