package ru.kpfu.itis.bagautdinov.controllers;

import ru.kpfu.itis.bagautdinov.services.OAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
@RequestMapping("/oauth2")
public class OAuthController {
    private final OAuthService oAuthService;

    @GetMapping("/callback")
    public String callback(@RequestParam String code){
        oAuthService.login(code);
        return "redirect:/";
    }
}
