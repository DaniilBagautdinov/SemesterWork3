package ru.kpfu.itis.bagautdinov.controllers;

import ru.kpfu.itis.bagautdinov.dto.CourseDto;
import ru.kpfu.itis.bagautdinov.dto.CourseInfoDto;
import ru.kpfu.itis.bagautdinov.dto.LessonDto;
import ru.kpfu.itis.bagautdinov.models.Course;
import ru.kpfu.itis.bagautdinov.models.User;
import ru.kpfu.itis.bagautdinov.security.details.UserSecurity;
import ru.kpfu.itis.bagautdinov.services.CourseService;
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
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @PostMapping("/createCourse")
    public String createCourse(@AuthenticationPrincipal UserSecurity userSecurity, CourseDto course) {
        Course newCourse = courseService.createCourse(course, userSecurity.getUser());
        return "redirect:/course/" + newCourse.getId();
    }

    @PutMapping("/updateCourse/{courseId}")
    public String updateCourse(CourseDto courseDto, @PathVariable Long courseId, @AuthenticationPrincipal UserSecurity userSecurity) {
        courseService.updateCourse(courseId, courseDto, userSecurity.getUser());
        return "redirect:/course/" + courseId;
    }

    @GetMapping("/updateCourse/{courseId}")
    public String getUpdateCourse(@PathVariable Long courseId, Model model) {
        Course course = courseService.getCourse(courseId);
        model.addAttribute("course", course);
        return "updateCourse";
    }

    @DeleteMapping("/deleteCourse/{courseId}")
    @ResponseBody
    public String deleteCourse(@PathVariable Long courseId, @AuthenticationPrincipal UserSecurity userSecurity) {
        courseService.deleteCourse(courseId, userSecurity.getUser());
        return "redirect:/";
    }

    @GetMapping("/createCourse")
    public String getCreateCourse() {
        return "createCourse";
    }

    @GetMapping("/{courseId}")
    public String getCourse(@PathVariable Long courseId, Model model, @AuthenticationPrincipal UserSecurity userSecurity) {
        Course course = courseService.getCourse(courseId);
        CourseInfoDto courseInfo = courseService.getCourseInfo(courseId, userSecurity.getUser());
        model.addAttribute("course", course);
        model.addAttribute("courseInfo", courseInfo);
        return "course";
    }

    @GetMapping("/{courseId}/joinToCourse")
    public String joinToCourse(@PathVariable Long courseId, @AuthenticationPrincipal UserSecurity userSecurity) {
        courseService.joinToCourse(courseId, userSecurity.getUser());
        return "redirect:/course/" + courseId;
    }

    @PostMapping("/{courseId}/createLesson")
    public String createLesson(@PathVariable Long courseId, LessonDto lessonDto, @AuthenticationPrincipal UserSecurity userSecurity, @RequestParam("file") MultipartFile multipart) {
        courseService.createLesson(courseId, lessonDto, multipart, userSecurity.getUser());
        return "redirect:/course/" + courseId;
    }

    @GetMapping("/myCourses")
    public String getMyCourses(@AuthenticationPrincipal UserSecurity userSecurity, Model model) {
        User user = userSecurity.getUser();
        List<Course> myCourses = courseService.getMyCourses(user);
        model.addAttribute("myCourses", myCourses);
        return "myCourses";
    }

    @ExceptionHandler({RuntimeException.class})
    public ModelAndView handleException(RuntimeException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);

        modelAndView.setViewName("error");
        return modelAndView;
    }
}
