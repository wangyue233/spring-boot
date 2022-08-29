package com.example.demo.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents(){
        return studentRepository.findAll();
    }

    public void addNewStudent(Student student) {
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(student.getEmail());
        if(studentOptional.isPresent()){
            throw new IllegalStateException("email taken");
        }
        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        boolean exists = studentRepository.existsById(studentId);
        if(!exists){
            throw new IllegalStateException(
                    "student with id " + studentId + " does not exis");
        }
        studentRepository.deleteById(studentId);
    }

    @Transactional
    public void updateStudent(Long studentId, String name, String email){
        studentRepository.findById(studentId).map(current -> {
            if (isUpdatedNeeded(current.getName(), name)){
            current.setName(name);
            }
            if(isUpdatedNeeded(current.getEmail(),email)){
            current.setEmail(email);
            }
            return current;
        }).orElseThrow(() -> new IllegalStateException("student with id " + studentId + " does not exis"));
    }

    private boolean isUpdatedNeeded(String current, String update){
        return update != null && update.length()>0 && !update.equals(current);
    }
}
