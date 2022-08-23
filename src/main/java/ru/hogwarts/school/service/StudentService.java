package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

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
        Optional<Student> student = studentRepository.findById(id);
        if(student.isEmpty()) {
            return null;
        }
        return student.get();
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public Set<Student> getStudentsByAge(int age) {
        return studentRepository.findByAge(age);
    }

    public boolean deleteStudent(long id) {
        if(studentRepository.findById(id).isPresent()) {
            studentRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}