package ru.hogwarts.school.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.StudentRepository;

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

    public List<Student> getAllStudents(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page-1, size);
        return studentRepository.findAll(pageRequest).getContent();
    }

    public Faculty getStudentsFaculty(long id) {
        return studentRepository.findById(id).orElseThrow(StudentNotFoundException::new).getFaculty();
    }

    public long getStudentsAmount() {
        return studentRepository.getStudentsAmount();
    }

    public int getAverageAge() {
        return studentRepository.getAverageAge();
    }

    public List<Student> getLastFiveStudents() {
        return studentRepository.getLastFiveStudents();
    }
}