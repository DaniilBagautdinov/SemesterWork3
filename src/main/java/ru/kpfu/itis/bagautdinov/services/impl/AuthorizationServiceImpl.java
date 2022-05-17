package ru.kpfu.itis.bagautdinov.services.impl;

import ru.kpfu.itis.bagautdinov.converters.DateConverter;
import ru.kpfu.itis.bagautdinov.dto.SignUpDto;
import ru.kpfu.itis.bagautdinov.models.User;
import ru.kpfu.itis.bagautdinov.repositories.UserRepository;
import ru.kpfu.itis.bagautdinov.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DateConverter dateConverter;

    @Override
    public void registration(SignUpDto signUpDto) {
        Optional<User> optionalAccount = userRepository.findByUsername(signUpDto.getUsername());

        if (optionalAccount.isEmpty()) {
            User user = User.builder()
                    .firstName(signUpDto.getFirstName())
                    .lastName(signUpDto.getLastName())
                    .username(signUpDto.getUsername())
                    .passwordHash(passwordEncoder.encode(signUpDto.getPassword()))
                    .registrationDate(dateConverter.convert(Calendar.getInstance().getTime()))
                    .build();

            userRepository.save(user);

        } else {
            throw new RuntimeException();
        }


    }

    @Override
    public void oauth2Registration(SignUpDto signUpDto) {
        Optional<User> optionalAccount = userRepository.findByUsername(signUpDto.getUsername());

        if (optionalAccount.isEmpty()) {
            User user = User.builder()
                    .firstName(signUpDto.getFirstName())
                    .lastName(signUpDto.getLastName())
                    .username(signUpDto.getUsername())
                    .registrationDate(dateConverter.convert(Calendar.getInstance().getTime()))
                    .build();

            userRepository.save(user);

        }
    }
}
