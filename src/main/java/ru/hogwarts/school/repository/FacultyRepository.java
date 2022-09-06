package ru.hogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.model.Faculty;

import java.util.Set;

@Repository
public interface FacultyRepository extends JpaRepository<Faculty, Integer> {

    Set<Faculty> findAllByColour(String colour);
    Set<Faculty> findAllByName(String name);
    Set<Faculty> findAllByNameAndColour(String name, String colour);
}
