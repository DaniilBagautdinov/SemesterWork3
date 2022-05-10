package ru.kpfu.itis.bagautdinov.services;


import ru.kpfu.itis.bagautdinov.dto.HomeworkInfoDto;
import ru.kpfu.itis.bagautdinov.models.Homework;
import ru.kpfu.itis.bagautdinov.models.User;

public interface HomeworkService {

    Homework getHomework(Long homeworkId, User user);

    void checkHomework(Long homeworkId, Integer score);

    HomeworkInfoDto getHomeworkInfo(Long homeworkId, User user);
}
