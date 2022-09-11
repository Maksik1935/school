package ru.hogwarts.school.controllers;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.AvatarRepository;
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.repositories.StudentRepository;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.FacultyService;
import ru.hogwarts.school.services.StudentService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
class StudentControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private FacultyService facultyService; // А если мне какой-то интерфейс не надо Мокать, все равно придется?) Нельзя же SpyBean на интерфейс повесить?
    @MockBean
    private FacultyRepository facultyRepository;
    @SpyBean
    private AvatarService avatarService;
    @MockBean
    private AvatarRepository avatarRepository;
    @SpyBean
    private StudentService studentService;
    @MockBean
    private StudentRepository studentRepository;


    private static Student student;
    private static JSONObject studentObject;

    static long id = 1;
    static String name = "Potter";
    static int age = 15;

    @BeforeAll
    public static void init() {
        student = new Student();
        student.setId(id);
        student.setName(name);
        student.setAge(age);

        studentObject = new JSONObject();
        studentObject.put("id", id);
        studentObject.put("name", name);
        studentObject.put("age", age);
    }

    @Test
    public void createTest() throws Exception {

        when(studentRepository.save(student)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }
    @Test
    public void findTest() throws Exception {

        when(studentRepository.findById(id)).thenReturn(Optional.of(student));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.age").value(age));

    }

    @Test
    public void deleteTest() throws Exception {
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student/" + id)
                        .content(studentObject.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTest() throws Exception {
        when(studentRepository.findAll()).thenReturn(new ArrayList<>(List.of(student)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getAll/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));
    }

    @Test
    public void geaByAgeTest() throws Exception {
        when(studentRepository.findByAgeBetween(10, 20)).thenReturn(new HashSet<>(Set.of(student)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/getByAge/?min=10&max=20")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].age").value(age));
    }

    @Test
    public void getStudentFacultyTest() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1);
        faculty.setName("griffindor");
        faculty.setColour("red");
        student.setFaculty(faculty);
        when(studentRepository.findById(id)).thenReturn(Optional.of(student));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student/" + id + "/faculty/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("griffindor"))
                .andExpect(jsonPath("$.colour").value("red"));
    }
}