package ru.kpfu.itis.bagautdinov.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.bagautdinov.controllers.CourseController;
import ru.kpfu.itis.bagautdinov.dto.DtoMapper;
import ru.kpfu.itis.bagautdinov.dto.HomeworkDto;
import ru.kpfu.itis.bagautdinov.repositories.LessonRepository;
import ru.kpfu.itis.bagautdinov.services.FilesService;
import ru.kpfu.itis.bagautdinov.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.bagautdinov.models.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final FilesService filesService;
    private final DtoMapper dtoMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @Override
    public void createTask(Long lessonId, Task task) {
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();
        lesson.setTask(task);
        lessonRepository.save(lesson);
    }

    @Override
    public void passHomework(Long lessonId, HomeworkDto homeworkDto, MultipartFile[] multipart, User user) {
        List<FileInfo> photos = filesService.upload(multipart);
        Lesson lesson = lessonRepository.findById(lessonId).orElseThrow();

        Homework homework = dtoMapper.homeworkDtoToHomework(homeworkDto);
        homework.setPhotos(photos);
        homework.setStudent(user);
        homework.setStatus(Homework.Status.UNCHECKED);
        homework.setLesson(lesson);

        lesson.getHomeworks().add(homework);
        lessonRepository.save(lesson);
    }

    @Override
    public Lesson getLesson(Long lessonId, User user) {
        Optional<Lesson> lesson = lessonRepository.findById(lessonId);
        if (lesson.isEmpty()) {
            LOGGER.warn("Такого урока не существует");
            throw new RuntimeException("Такого урока не существует");
        } else if (lesson.get().getCourse().getSubscribers().stream().noneMatch(x -> x.getId().equals(user.getId())) && !user.getId().equals(lesson.get().getCourse().getCreator().getId())) {
            throw new RuntimeException();
        }
        return lesson.get();
    }
}
