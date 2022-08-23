package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.Optional;
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
        return facultyRepository.findById(id).orElseThrow();
    }

    public Faculty updateFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public boolean deleteFaculty(int id) {
        if (facultyRepository.findById(id).isPresent()) {
            facultyRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    public Set<Faculty> getFacultiesByColour(String colour) {
        return facultyRepository.findByColour(colour);
    }
}