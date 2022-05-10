package ru.kpfu.itis.bagautdinov.controllers;

import ru.kpfu.itis.bagautdinov.dto.SignUpDto;
import ru.kpfu.itis.bagautdinov.services.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/registration")
    public ResponseEntity<?> registration(@Valid SignUpDto signUpDto) {
        authorizationService.registration(signUpDto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
