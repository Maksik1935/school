package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controllers.StudentController;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;

import javax.swing.text.html.parser.Entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTest {
 /*   @LocalServerPort
    private int port;
    @Autowired
    private StudentController studentController;
    @Autowired
    private TestRestTemplate restTemplate;

    private Student student;
    private Faculty faculty;

    static long id = 1;
    static String name = "Potter";
    static int age = 15;

    @BeforeEach
    private void init() {
        student = new Student();
        student.setId(id);
        student.setAge(age);
        student.setName(name);
        student.setFaculty(faculty);

        faculty = new Faculty();
        faculty.setName("griffindor");
    }
    @Test
    void contextLoads() {
        Assertions.assertThat(studentController).isNotNull();
    }

    @Test
    public void createTest() {
        Assertions
                .assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class))
                .isEqualTo(student);
    }

    @Test
    public void findTest() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/17", Student.class))
                .isEqualTo(student);
    }

    @Test
    public void updateTest() {
        this.restTemplate.put("http://localhost:" + port + "/student", student, Student.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/17", Student.class))
                .isEqualTo(student);
    }

   @Test
    public void deleteTest() {
        this.restTemplate.delete("http://localhost:" + port + "/student/17", student, Student.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/17", ResponseEntity.class))
                .isEqualTo(new ResponseEntity(HttpStatus.BAD_REQUEST));

    @Test
    public void getAllTest() {

        List<Student> list = new ArrayList<>(List.of(student));
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/getAll", List.class))
                .isEqualTo(list);
    }

    @Test
    public void getByAgeTest() {

        Set<Student> set = new HashSet<>(Set.of(student));
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/getByAge/", Set.class))
                .isEqualTo(set);
    }

    @Test
    public void getStudentFacultyTest() {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/17/faculty/", Faculty.class))
                .isEqualTo(faculty);
    }

*/
}