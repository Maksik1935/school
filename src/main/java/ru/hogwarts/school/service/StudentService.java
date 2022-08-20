package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exception.IncorrectStudentParametersException;
import ru.hogwarts.school.model.Student;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class StudentService {
    private final Map<Long, Student> students = new HashMap<>();
    private long idCount;

    /*public Student addStudent(Student student) {    // Вот эту абракадабру надо было написать? Серьезно? И так всю валюдацию?
        student.setId(++idCount);
        if (student.getAge() <= 10 || student.getAge() >= 30 ||
        student.getName() == null || student.getName() == "" ||
        student.getFaculty() <=0 || student.getFaculty() >= FacultyService.getIdCount()) {
            throw new IncorrectStudentParametersException();
        }
        return students.put(idCount, student);
    }*/

    public Student addStudent(Student student) {
        student.setId(++idCount);
        return students.put(idCount, student);
    }


    public Student findStudent(long id) {
        return students.get(id);
    }

    public Student updateStudent(long id, Student student) {
        students.remove(id);
        student.setId(id);
        return students.put(id, student);
    }

    public Set<Student> getStudentsByAge(int age) {
        Set<Student> filteredSet = new HashSet();
        for (Student s : students.values()) {
            if (s.getAge() == age) {
                filteredSet.add(s);
            }
        }
        return filteredSet;
    }

    public boolean deleteStudent(long id) {
        if(students.containsKey(id)) {
            students.remove(id);
            return true;
        }
        return false;
    }
}