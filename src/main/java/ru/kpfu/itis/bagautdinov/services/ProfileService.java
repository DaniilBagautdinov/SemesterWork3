package ru.kpfu.itis.bagautdinov.services;

import ru.kpfu.itis.bagautdinov.dto.SignUpDto;
import ru.kpfu.itis.bagautdinov.dto.UserDto;
import ru.kpfu.itis.bagautdinov.models.FileInfo;
import ru.kpfu.itis.bagautdinov.models.User;

import java.util.List;

public interface ProfileService {

    void editProfile(User user, SignUpDto userDto, FileInfo fileInfo);

    List<User> getTeachers();

    UserDto getUserById(Long userId);

    UserDto getTeacherById(Long userId);
}
