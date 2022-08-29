package com.example.demo.student;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
//this interface is responsible for data access
public interface StudentRepository
        extends JpaRepository<Student,Long> {
}
