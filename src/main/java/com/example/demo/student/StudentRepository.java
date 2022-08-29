package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
//this interface is responsible for data access
public interface StudentRepository
        extends JpaRepository<Student,Long> {
//    SELECT * From Student WHERE Student.email = email;
    Optional<Student> findStudentByEmail(String email);
}
