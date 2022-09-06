package ru.hogwarts.school.controller;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import javax.validation.Valid;
import java.util.NoSuchElementException;
import java.util.Set;

@RestController
@RequestMapping("/faculty")
public class FacultyController {
    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public ResponseEntity<Faculty> createStudent(@Valid @RequestBody Faculty faculty) {
        Faculty createdFaculty = facultyService.addFaculty(faculty);
        return ResponseEntity.ok(createdFaculty);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> findStudent(@PathVariable int id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(faculty);
    }

    @PutMapping
    public ResponseEntity<Faculty> updateStudent(@Valid @RequestBody Faculty faculty) {
        Faculty updatedFaculty = facultyService.updateFaculty(faculty);
        return ResponseEntity.ok(updatedFaculty);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Faculty> deleteStudent(@PathVariable int id) {
        if (!facultyService.deleteFaculty(id)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getByParams/")
    public ResponseEntity<Set<Faculty>> getFacultiesByParams(@RequestParam(required = false) String name,
                                                             @RequestParam(required = false) String colour) {
        if (!name.isBlank() && colour.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultiesByName(name));
        } else if (name.isBlank() && !colour.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultiesByColour(colour));
        } else if (!name.isBlank() && !colour.isBlank()) {
            return ResponseEntity.ok(facultyService.getFacultiesByNameAndColour(name, colour));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();   // Наверное, нужно эту ситуацию было через исключение обработать? Подсказать, что нету хотя бы одного параметра.
        }
    }

    @GetMapping("/getStudents/")
    public ResponseEntity<Set<Student>> getStudents(int id) {
        return ResponseEntity.ok(facultyService.getStudentsByFacultyId(id));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> invalidValid() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Incorrect faculty params"));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorMessage> notFoundElement() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Element not found"));
    }
}