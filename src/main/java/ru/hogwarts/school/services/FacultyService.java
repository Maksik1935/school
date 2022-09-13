package ru.hogwarts.school.services;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.List;
import java.util.Set;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(int id) {
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(int id) {
        facultyRepository.delete(findFaculty(id));
    }

    public List<Faculty> getAll() {
        return facultyRepository.findAll();
    }

    public Set<Faculty> getFacultiesByNameOrColour(String nameOrColour) {
        return facultyRepository.findAllByNameIgnoreCaseOrColourIgnoreCase(nameOrColour, nameOrColour);
    }


    public Set<Student> getStudentsByFacultyId(int id) {
        return facultyRepository.findAllById(id);
    }
}