package com.example.demo.student;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    private StudentService studentService;
    @Mock
    private StudentRepository studentRepository;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    void canGetAllStudents() {
//        when
        studentService.getStudents();
//        then
        verify(studentRepository).findAll();
    }

    @Test
    void canAddNewStudent() {
//        given
        Student student =  new Student(
                "mariam",
                LocalDate.of(2000, Month.APRIL,5),
                "mariam.jamal@gmail.com"
        );
//        when
        studentService.addNewStudent(student);
//        then
        ArgumentCaptor<Student> studentArgumentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository).save(studentArgumentCaptor.capture());
        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(capturedStudent).isEqualTo(student);
    }

    @Test
    void willThrowWhenEmailIsTaken() {
//        given
        Student student =  new Student(
                "mariam",
                LocalDate.of(2000, Month.APRIL,5),
                "mariam.jamal@gmail.com"
        );
        when(studentRepository.findStudentByEmail(anyString()))
                .thenReturn(java.util.Optional.of(student));
//        when
//        then
      assertThatThrownBy(
              ()->studentService.addNewStudent(student))
              .isInstanceOf(IllegalStateException.class)
              .hasMessage("email taken");
      verify(studentRepository,never()).save(any());
    }

    @Test
    void deleteStudent() {
    }

    @Test
    void updateStudent() {
    }
}