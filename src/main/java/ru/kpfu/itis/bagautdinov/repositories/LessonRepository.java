package ru.kpfu.itis.bagautdinov.repositories;

import ru.kpfu.itis.bagautdinov.models.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
