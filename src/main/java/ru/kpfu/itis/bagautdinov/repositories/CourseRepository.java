package ru.kpfu.itis.bagautdinov.repositories;


import ru.kpfu.itis.bagautdinov.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository  extends JpaRepository<Course, Long> {

}
