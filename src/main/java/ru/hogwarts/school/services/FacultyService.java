package ru.hogwarts.school.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.exceptions.FacultyNotFoundException;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;
import ru.hogwarts.school.repositories.FacultyRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class FacultyService {

    private final Logger logger = LoggerFactory.getLogger(FacultyService.class);
    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Invoked addFaculty");
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(int id) {
        logger.info("Invoked findFaculty");
        return facultyRepository.findById(id).orElseThrow(FacultyNotFoundException::new);
    }

    public Faculty updateFaculty(Faculty faculty) {
        logger.info("Invoked updateFaculty");
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(int id) {
        logger.info("Invoked deleteFaculty");
        facultyRepository.delete(findFaculty(id));
    }

    public List<Faculty> getAll() {
        logger.info("Invoked getAll");
        return facultyRepository.findAll();
    }


    public Set<Faculty> getFacultiesByNameOrColour(String nameOrColour) {
        logger.info("Invoked FindAllByNameOrColour");
        return facultyRepository.findAllByNameIgnoreCaseOrColourIgnoreCase(nameOrColour, nameOrColour);
    }


    public Set<Student> getStudentsByFacultyId(int id) {
        logger.info("Invoked findAllById");
        return facultyRepository.findAllById(id);
    }

    public String getFacultyWithLongerName() {
        return getAll().stream()     // А как вообще сюда выброс исключения засунуть?)
                .map(Faculty::getName)
                .max(Comparator.comparingInt(String::length)).get();
    }
}