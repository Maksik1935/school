package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.models.Faculty;
import ru.hogwarts.school.models.Student;

import java.util.Set;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    Set<Faculty> findAllByNameOrColourIgnoreCase(String name, String colour);
    Set<Student> findAllById(long id);
}
