package ru.hogwarts.school.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.FacultyService;

import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    private final Logger logger = LoggerFactory.getLogger(FacultyController.class);

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }


    @PostMapping
    public ResponseEntity<Faculty> createFaculty(@Valid @RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> findFaculty(@PathVariable int id) {
        return ResponseEntity.ok(facultyService.findFaculty(id));
    }

    @PutMapping
    public ResponseEntity<Faculty> updateFaculty(@Valid @RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteFaculty(@PathVariable int id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll/")
    public List<Faculty> getAll() {
        return facultyService.getAll();
    }

    @GetMapping("/getByParams/")
    public ResponseEntity<?> getFacultiesByNameOrColour(@RequestParam String nameOrColour) {
        if (nameOrColour.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect params");
        } else {
            return ResponseEntity.ok(facultyService.getFacultiesByNameOrColour(nameOrColour));
        }
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<Set<Student>> getStudents(@PathVariable int id) {
        return ResponseEntity.ok(facultyService.getStudentsByFacultyId(id));
    }

    @GetMapping("/get-longer-name")
    public ResponseEntity<String> getFacultyWithLongerName () {
        return ResponseEntity.ok(facultyService.getFacultyWithLongerName());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> invalidValid() {
        logger.warn("Incorrect faculty params");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Incorrect faculty params"));
    }

    @ExceptionHandler(FacultyNotFoundException.class)
    public ResponseEntity<ErrorMessage> notFoundElement() {
        logger.warn("Element not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Element not found"));
    }
}