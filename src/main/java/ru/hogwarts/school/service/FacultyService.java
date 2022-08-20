package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class FacultyService {
    private final Map<Integer, Faculty> faculties = new HashMap<>();
    private static int idCount;

    public Faculty addFaculty(Faculty faculty) {
        faculty.setId(++idCount);
        return faculties.put(idCount, faculty);
    }

    public Faculty findFaculty(int id) {
        return faculties.get(id);
    }

    public Faculty updateFaculty(int id, Faculty faculty) {
        faculties.remove(id);
        faculty.setId(id);
        return faculties.put(id, faculty);
    }

    public boolean deleteFaculty(int id) {
        if (faculties.containsKey(id)) {
            faculties.remove(id);
            return true;
        }
        return false;
    }

    public Set<Faculty> getFacultiesByColour(String colour) {
        Set<Faculty> filteredSet = new HashSet<>();
        for (Faculty f : faculties.values()) {
            if (f.getColour() == colour) {
                filteredSet.add(f);
            }
        }
        return filteredSet;
    }

    public static int getIdCount() {
        return idCount;
    }
}