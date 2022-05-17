package ru.kpfu.itis.bagautdinov.controllers;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.kpfu.itis.bagautdinov.dto.SignUpDto;
import ru.kpfu.itis.bagautdinov.models.Course;
import ru.kpfu.itis.bagautdinov.models.FileInfo;
import ru.kpfu.itis.bagautdinov.models.User;
import ru.kpfu.itis.bagautdinov.security.details.UserSecurity;
import ru.kpfu.itis.bagautdinov.services.CourseService;
import ru.kpfu.itis.bagautdinov.services.FilesService;
import ru.kpfu.itis.bagautdinov.services.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {

    private final ProfileService profileService;
    private final FilesService filesService;
    private final CourseService courseService;
    private final Logger LOGGER = LoggerFactory.getLogger(CourseController.class);

    @GetMapping("/")
    public String index(Model model) {
        List<Course> courses = courseService.getAllCourses();
        model.addAttribute("courses", courses);
        return "homepage";
    }

    @GetMapping("/*")
    public void getOther(){
        throw new RuntimeException("Страница не найдена");
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/profilepage")
    public String getProfile(){
        return "profilepage";
    }


    @GetMapping("/editpage")
    public String editpage() {
        return "editpage";
    }

    @PostMapping("/editpage")
    public String editProfile(SignUpDto signUpDto, @RequestParam("avatar") MultipartFile multipart, @AuthenticationPrincipal UserSecurity userSecurity) {
        User user = userSecurity.getUser();
        FileInfo fileInfo = null;
        if (!multipart.isEmpty()) fileInfo = filesService.upload(multipart);
        profileService.editProfile(user, signUpDto, fileInfo);
        return "redirect:/profilepage";
    }

    @ExceptionHandler({RuntimeException.class})
    public ModelAndView handleException(RuntimeException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);
        LOGGER.warn("Страница не найдена");
        modelAndView.setViewName("error");
        return modelAndView;
    }
}


