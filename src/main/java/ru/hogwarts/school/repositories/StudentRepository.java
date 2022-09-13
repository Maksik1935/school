package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.hogwarts.school.models.Student;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Set;

@Repository
public interface StudentRepository extends PagingAndSortingRepository<Student, Long> {
    Set<Student> findByAgeBetween(int min, int max);
    List<Student> findAll(Pageable pageable);
    @Query(value = "SELECT COUNT(*) FROM student", nativeQuery = true)
    long getStudentsAmount();
    @Query(value = "SELECT AVG (age) FROM student", nativeQuery = true)
    int getAverageAge();
    @Query(value = "SELECT * FROM student ORDER BY id DESC LIMIT 5", nativeQuery = true)
    List<Student> getLastFiveStudents();
}

