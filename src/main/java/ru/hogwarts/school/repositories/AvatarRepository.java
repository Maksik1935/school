package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.hogwarts.school.models.Avatar;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {

    Optional<Avatar> findByStudentId(long id);

}
