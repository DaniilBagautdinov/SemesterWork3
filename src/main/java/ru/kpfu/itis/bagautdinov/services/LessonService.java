package ru.kpfu.itis.bagautdinov.services;

import ru.kpfu.itis.bagautdinov.dto.HomeworkDto;
import ru.kpfu.itis.bagautdinov.models.Lesson;
import ru.kpfu.itis.bagautdinov.models.Task;
import ru.kpfu.itis.bagautdinov.models.User;
import org.springframework.web.multipart.MultipartFile;

public interface LessonService {

    Lesson getLesson(Long lessonId, User user);

    void createTask(Long lessonId, Task task);

    void passHomework(Long lessonId, HomeworkDto homeworkDto, MultipartFile[] multipart, User user);
}
