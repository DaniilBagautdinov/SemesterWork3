package ru.kpfu.itis.bagautdinov.services;

import ru.kpfu.itis.bagautdinov.dto.SignUpDto;

public interface AuthorizationService {

    void registration(SignUpDto customerDto);

    void oauth2Registration(SignUpDto signUpDto);
}
