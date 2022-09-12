package ru.hogwarts.school.controllers;

import org.springdoc.api.ErrorMessage;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.exceptions.StudentNotFoundException;
import ru.hogwarts.school.models.Avatar;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.services.AvatarService;
import ru.hogwarts.school.services.StudentService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student student) {
        Student createdStudent = studentService.addStudent(student);
        return ResponseEntity.ok(createdStudent);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> findStudent(@PathVariable long id) {
        return ResponseEntity.ok(studentService.findStudent(id));
    }

    @PutMapping
    public ResponseEntity<Student> updateStudent(@Valid @RequestBody Student student) {
        Student updatedStudent = studentService.updateStudent(student);
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @GetMapping("/getByAge/")
    public ResponseEntity<Set<Student>> getStudentByAge(@RequestParam int min, @RequestParam int max) {
        return ResponseEntity.ok(studentService.getStudentDyAge(min, max));
    }

    @GetMapping("/{id}/faculty/")
    public ResponseEntity<?> getStudentFaculty(@PathVariable long id) {
        if(studentService.getStudentsFaculty(id) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Faculty was not found");
        }
        return ResponseEntity.ok(studentService.getStudentsFaculty(id));
    }

    @PostMapping(value = "/{id}/avatar/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Boolean> uploadAvatar(@PathVariable long id, @RequestParam MultipartFile avatar) throws IOException {
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/avatar/getFromDb")
    public ResponseEntity<?> getAvatarFromDb(@PathVariable long id) {
        Avatar avatar = avatarService.findAvatar(id);
        if (avatar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avatar not found");
        } else {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
            headers.setContentLength(avatar.getData().length);
            return ResponseEntity.ok().headers(headers).body(avatar.getData());
        }
    }

    @GetMapping("{id}/avatar/getFromFile/") // не надо ли всю эту ботву в сервис засунуть?
    public ResponseEntity<?> getAvatarFromFile(@PathVariable long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findAvatar(id);
        if (avatar == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Avatar not found");
        }
        Path path = Path.of(avatar.getFilePath());
        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setStatus(200);
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> invalidValid() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage("Incorrect student params"));
    }

    @ExceptionHandler(StudentNotFoundException.class)
    public ResponseEntity<ErrorMessage> elementNotFound () {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage("Student not Found"));
    }

}