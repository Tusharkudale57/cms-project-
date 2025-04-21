package com.example.login.service;

import com.example.login.model.Student;
import com.example.login.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {
    
    private final StudentRepository studentRepository;
    
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    
    @Transactional
    public Student createStudent(Student student) {
        // Check if email already exists
        if (studentRepository.existsByEmail(student.getEmail())) {
            log.error("Email already in use: {}", student.getEmail());
            throw new IllegalArgumentException("Email already in use");
        }
        
        
        if (studentRepository.existsByRollNumber(student.getRollNumber())) {
            log.error("Roll number already in use: {}", student.getRollNumber());
            throw new IllegalArgumentException("Roll number already in use");
        }
        
        return studentRepository.save(student);
    }
    
    @Transactional
    public Optional<Student> updateStudent(Long id, Student studentDetails) {
        return studentRepository.findById(id)
                .map(existingStudent -> {
                    
                    if (!existingStudent.getEmail().equals(studentDetails.getEmail()) && 
                            studentRepository.existsByEmail(studentDetails.getEmail())) {
                        log.error("Email already in use: {}", studentDetails.getEmail());
                        throw new IllegalArgumentException("Email already in use");
                    }
                    
                    existingStudent.setName(studentDetails.getName());
                    existingStudent.setEmail(studentDetails.getEmail());
                    existingStudent.setPhone(studentDetails.getPhone());
                    existingStudent.setRollNumber(studentDetails.getRollNumber());
                    existingStudent.setCourseName(studentDetails.getCourseName());
                    existingStudent.setStaff(studentDetails.getStaff());
                    
                    return studentRepository.save(existingStudent);
                });
    }
    
    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            log.error("Student not found with id: {}", id);
            throw new IllegalArgumentException("Student not found with id: " + id);
        }
        
        studentRepository.deleteById(id);
    }
    
    public List<Student> getStudentsByStaffId(Long staffId) {
        return studentRepository.findByStaffId(staffId);
    }
}