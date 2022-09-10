package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.sql.Statement;
import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }


    public Student findStudent(long id) {
        return studentRepository.findById(id).orElseThrow();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public boolean deleteStudent(long id) {
        if(studentRepository.findById(id).isPresent()) {
            studentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Set<Student> getStudentDyAge(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Faculty getStudentsFaculty(long id) {
        return studentRepository.findById(id).orElse(null).getFaculty();
    }
}