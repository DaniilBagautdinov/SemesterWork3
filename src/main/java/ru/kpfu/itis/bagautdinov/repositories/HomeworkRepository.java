package ru.kpfu.itis.bagautdinov.repositories;

import ru.kpfu.itis.bagautdinov.models.Homework;
import ru.kpfu.itis.bagautdinov.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeworkRepository extends JpaRepository<Homework, Long> {
    Optional<Homework> findByStudent(User user);
}
