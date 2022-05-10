package ru.kpfu.itis.bagautdinov.services.impl;

import ru.kpfu.itis.bagautdinov.controllers.CourseController;
import ru.kpfu.itis.bagautdinov.dto.CourseDto;
import ru.kpfu.itis.bagautdinov.dto.CourseInfoDto;
import ru.kpfu.itis.bagautdinov.dto.DtoMapper;
import ru.kpfu.itis.bagautdinov.dto.LessonDto;
import ru.kpfu.itis.bagautdinov.repositories.CourseRepository;
import ru.kpfu.itis.bagautdinov.repositories.UserRepository;
import ru.kpfu.itis.bagautdinov.services.CourseService;
import ru.kpfu.itis.bagautdinov.services.FilesService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.kpfu.itis.bagautdinov.models.Course;
import ru.kpfu.itis.bagautdinov.models.FileInfo;
import ru.kpfu.itis.bagautdinov.models.Lesson;
import ru.kpfu.itis.bagautdinov.models.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final DtoMapper dtoMapper;
    private final FilesService filesService;
    private final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);


    @Override
    public Course createCourse(CourseDto courseDto, User user) {
        Course course = dtoMapper.courseDtoToCourse(courseDto);
        course.setCreator(user);
        course.setSubscribers(Collections.singletonList(user));
        return courseRepository.save(course);
    }

    @Override
    public Course getCourse(Long courseId) {
        Optional<Course> course = courseRepository.findById(courseId);
        if (course.isEmpty()){
            LOGGER.warn("Такого курса не существует");
            throw new RuntimeException("Такого курса не существует");
        }
        return course.get();
    }

    @Override
    public List<Course> getMyCourses(User user) {
        return userRepository.findById(user.getId()).orElseThrow().getCourses();
    }

    @Override
    public void createLesson(Long courseId, LessonDto lessonDto, MultipartFile multipart, User user) {
        Course course = courseRepository.findById(courseId).orElseThrow();

        if (!course.getCreator().getId().equals(user.getId())) throw new RuntimeException();

        FileInfo video = filesService.upload(multipart);

        Lesson lesson = dtoMapper.lessonDtoToLesson(lessonDto);
        lesson.setCourse(course);
        lesson.setVideo(video);

        course.getLessons().add(lesson);

        courseRepository.save(course);
    }


    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public void joinToCourse(Long courseId, User user) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        if (course.getSubscribers().stream().anyMatch(x->x.getId().equals(user.getId()))) throw new RuntimeException();

        course.getSubscribers().add(user);

        courseRepository.save(course);
    }

    @Override
    public CourseInfoDto getCourseInfo(Long courseId, User user) {
        Course course = courseRepository.findById(courseId).orElseThrow();
        user = userRepository.findById(user.getId()).orElseThrow();
        boolean inCourse = user.getCourses().contains(course);
        boolean isCreator = course.getCreator().getId().equals(user.getId());
        int studentsCount = course.getSubscribers().size();
        return CourseInfoDto.builder()
                .inCourse(inCourse)
                .isCreator(isCreator)
                .studentsCount(studentsCount)
                .build();
    }

    @Override
    public void updateCourse(Long courseId, CourseDto courseDto, User user) {
        Course course = courseRepository.findById(courseId).orElseThrow();

        if (!course.getCreator().getId().equals(user.getId())) throw new RuntimeException();

        course.setTitle(courseDto.getTitle());
        course.setDescription(courseDto.getDescription());

        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long courseId, User user) {
        Course course = courseRepository.findById(courseId).orElseThrow();

        if (!course.getCreator().getId().equals(user.getId())) throw new RuntimeException();


        courseRepository.deleteById(courseId);
    }
}
