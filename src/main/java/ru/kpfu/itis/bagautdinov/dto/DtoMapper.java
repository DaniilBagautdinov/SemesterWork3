package ru.kpfu.itis.bagautdinov.dto;

import ru.kpfu.itis.bagautdinov.models.Course;
import ru.kpfu.itis.bagautdinov.models.Homework;
import ru.kpfu.itis.bagautdinov.models.Lesson;
import ru.kpfu.itis.bagautdinov.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    UserDto userToUserDto(User user);

    Course courseDtoToCourse(CourseDto courseDto);

    Lesson lessonDtoToLesson(LessonDto lessonDto);

    Homework homeworkDtoToHomework(HomeworkDto homeworkDto);
}
