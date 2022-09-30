package ru.hogwarts.school.services;

import liquibase.pro.packaged.S;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.StudentRepository;

import java.util.*;
import java.util.stream.Collectors;

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
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
    }

    public Student updateStudent(Student student) {
        return studentRepository.save(student);
    }

    public void deleteStudent(long id) {
        studentRepository.delete(findStudent(id));
    }

    public Set<Student> getStudentDyAge(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Faculty getStudentsFaculty(long id) {
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new).getFaculty();
    }

    public long getStudentsAmount() {
        return studentRepository.getStudentsAmount();
    }

    public int getAverageAge() {
        // return studentRepository.getAverageAge();

        // Просто не стал переделывать уже готовый Query запрос. По-хорошему тут double надо возвращать.
        return (int) getAllStudents().stream()
                .mapToInt(s -> s.getAge())
                .average()
                .getAsDouble();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }

    public List<String> getAllWithNameStartsByA() {
        return getAllStudents().stream()
                .map(s -> s.getName().toUpperCase())
                .filter(s -> s.startsWith("A"))
                .sorted()
                .collect(Collectors.toList());
    }
}