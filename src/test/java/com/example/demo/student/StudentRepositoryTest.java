package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class StudentRepositoryTest {
    @Autowired
    private StudentRepository studentRepository;

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @Test
    void itShouldFindStudentByEmail() {
//        given
        String email = "mariam.jamal@gmail.com";
        Student mariam =  new Student(
                "mariam",
                LocalDate.of(2000, Month.APRIL,5),
                "mariam.jamal@gmail.com"
        );
        studentRepository.save(mariam);
//        when
        Optional<Student> expected = studentRepository.findStudentByEmail(email);
//        then
        assertThat(expected.isPresent()).isTrue();
        assertThat(expected.get()).isEqualTo(mariam);
    }

    @Test
    void itShouldCheckStudentEmailNotExist() {
//        given
        String email = "mariam.jamal@gmail.com";
//        when
        Optional<Student> expected = studentRepository.findStudentByEmail(email);
//        then
        assertThat(expected.isPresent()).isFalse();

    }
}