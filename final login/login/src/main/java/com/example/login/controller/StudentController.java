package com.example.login.controller;

import com.example.login.dto.MessageResponse;
import com.example.login.model.Student;
import com.example.login.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/students")
@RequiredArgsConstructor
@Slf4j
public class StudentController {
    
    private final StudentService studentService;
    
    @GetMapping("/get/all")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Student>> getAllStudents() {
        log.info("Getting all students");
        return ResponseEntity.ok(studentService.getAllStudents());
    }
    
    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF') or hasRole('STUDENT')")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        log.info("Getting student with ID: {}", id);
        return studentService.getStudentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        log.info("Creating new student");
        try {
            Student createdStudent = studentService.createStudent(student);
            return ResponseEntity.ok(createdStudent);
        } catch (Exception e) {
            log.error("Error creating student: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse("Error creating student: " + e.getMessage()));
        }
    }
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<?> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
        log.info("Updating student with ID: {}", id);
        return studentService.updateStudent(id, studentDetails)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteStudent(@PathVariable Long id) {
        log.info("Deleting student with ID: {}", id);
        try {
            studentService.deleteStudent(id);
            return ResponseEntity.ok(new MessageResponse("Student deleted successfully"));
        } catch (Exception e) {
            log.error("Error deleting student: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }
    
    @GetMapping("/byStaff/{staffId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('STAFF')")
    public ResponseEntity<List<Student>> getStudentsByStaffId(@PathVariable Long staffId) {
        log.info("Getting students by staff ID: {}", staffId);
        return ResponseEntity.ok(studentService.getStudentsByStaffId(staffId));
    }
}
