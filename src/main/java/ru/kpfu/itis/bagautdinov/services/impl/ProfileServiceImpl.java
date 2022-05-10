package ru.kpfu.itis.bagautdinov.services.impl;

import ru.kpfu.itis.bagautdinov.dto.DtoMapper;
import ru.kpfu.itis.bagautdinov.dto.SignUpDto;
import ru.kpfu.itis.bagautdinov.dto.UserDto;
import ru.kpfu.itis.bagautdinov.models.FileInfo;
import ru.kpfu.itis.bagautdinov.models.User;
import ru.kpfu.itis.bagautdinov.repositories.UserRepository;
import ru.kpfu.itis.bagautdinov.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DtoMapper dtoMapper;

    @Override
    public void editProfile(User user, SignUpDto userDto, FileInfo fileInfo) {
        if (!userDto.getFirstName().equals("")) user.setFirstName(userDto.getFirstName());
        if (!userDto.getLastName().equals("")) user.setLastName(userDto.getLastName());
        if (!userDto.getPassword().equals("")) user.setPasswordHash(passwordEncoder.encode(userDto.getPassword()));
        if (fileInfo !=null) user.setAvatar(fileInfo);
        userRepository.save(user);
    }

    @Override
    public List<User> getTeachers() {
        return null;
    }


    @Override
    public UserDto getUserById(Long userId) {
        return dtoMapper.userToUserDto(userRepository.findById(userId).orElseThrow());
    }

    @Override
    public UserDto getTeacherById(Long userId) {
        return null;
    }
}
