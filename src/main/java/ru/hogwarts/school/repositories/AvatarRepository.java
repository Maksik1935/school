package ru.hogwarts.school.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.hogwarts.school.models.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Integer> {

    Optional<Avatar> findByStudentId(long id);

}
