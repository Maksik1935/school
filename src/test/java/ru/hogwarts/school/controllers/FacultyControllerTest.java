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
import ru.hogwarts.school.repositories.FacultyRepository;
import ru.hogwarts.school.services.FacultyService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FacultyController.class)
class FacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private FacultyService facultyService;
    @MockBean
    private FacultyRepository facultyRepository;

    private static Faculty faculty;
    private static JSONObject facultyObject;

    static int id = 1;
    static String name = "griffindor";
    static String colour = "red";

    @BeforeAll
    public static void init() {
        faculty = new Faculty();
        faculty.setId(id);
        faculty.setName(name);
        faculty.setColour(colour);

        facultyObject = new JSONObject();
        facultyObject.put("id", id);
        facultyObject.put("name", name);
        facultyObject.put("colour", colour);
    }

    @Test
    public void createTest() throws Exception {

        when(facultyRepository.save(faculty)).thenReturn(faculty); // как это можно было засунуть в init? он же статик должен быть

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/faculty")
                        .content(facultyObject.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.colour").value(colour));

    }

    @Test
    public void findTest() throws Exception {
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/" + id)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.colour").value(colour));
    }

    @Test
    public void deleteTest() throws Exception {
        when(facultyRepository.findById(id)).thenReturn(Optional.of(faculty));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty/" + id)
                .content(facultyObject.toString())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTest() throws Exception {
        when(facultyRepository.findAll()).thenReturn(new ArrayList<>(List.of(faculty)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/getAll/")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(id))
                .andExpect(jsonPath("$[0].name").value(name))
                .andExpect(jsonPath("$[0].colour").value(colour));
    }

    @Test
    public void getByParamsTest() throws Exception {
        when(facultyRepository.findAllByNameOrColour(name, name)).thenReturn(new HashSet<>(Set.of(faculty)));
        mockMvc.perform(MockMvcRequestBuilders
                .get("/faculty/getByParams/?nameOrColour=" + name)
                .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].id").value(id))
                    .andExpect(jsonPath("$[0].name").value(name))
                    .andExpect(jsonPath("$[0].colour").value(colour));

    }

    @Test
    public void getStudentsTest() throws Exception {
        Student student = new Student();
        student.setAge(20);
        student.setName("Potter");
        student.setId(1);
        when(facultyRepository.findAllById(id)).thenReturn(new HashSet<>(Set.of(student)));

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty/" + student.getId() + "/students")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(student.getId()))
                .andExpect(jsonPath("$[0].name").value(student.getName()))
                .andExpect(jsonPath("$[0].age").value(student.getAge()));

    }
}