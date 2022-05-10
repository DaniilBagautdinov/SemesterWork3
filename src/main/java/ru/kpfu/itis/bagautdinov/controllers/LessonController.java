package ru.kpfu.itis.bagautdinov.controllers;

import ru.kpfu.itis.bagautdinov.dto.CourseInfoDto;
import ru.kpfu.itis.bagautdinov.dto.HomeworkDto;
import ru.kpfu.itis.bagautdinov.dto.HomeworkInfoDto;
import ru.kpfu.itis.bagautdinov.models.Homework;
import ru.kpfu.itis.bagautdinov.models.Lesson;
import ru.kpfu.itis.bagautdinov.models.Task;
import ru.kpfu.itis.bagautdinov.security.details.UserSecurity;
import ru.kpfu.itis.bagautdinov.services.CourseService;
import ru.kpfu.itis.bagautdinov.services.HomeworkService;
import ru.kpfu.itis.bagautdinov.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
@RequestMapping("/lesson")
public class LessonController {

    private final LessonService lessonService;
    private final CourseService courseService;
    private final HomeworkService homeworkService;

    @GetMapping("/{lessonId}")
    public String getLesson(@AuthenticationPrincipal UserSecurity userSecurity,
                            Model model,
                            @PathVariable Long lessonId) {
        Lesson lesson = lessonService.getLesson(lessonId, userSecurity.getUser());
        CourseInfoDto courseInfo = courseService.getCourseInfo(lesson.getCourse().getId(), userSecurity.getUser());

        HomeworkInfoDto homeworkInfo;
        Optional<Homework> optionalHomework = lesson.getHomeworks().stream().filter(x -> x.getStudent().getId().equals(userSecurity.getUser().getId())).findFirst();
        if (optionalHomework.isEmpty())
            homeworkInfo = homeworkService.getHomeworkInfo(null, userSecurity.getUser());
        else
            homeworkInfo = homeworkService.getHomeworkInfo(optionalHomework.get().getId(), userSecurity.getUser());
        model.addAttribute("homeworkInfo", homeworkInfo);
        model.addAttribute("courseInfo", courseInfo);
        model.addAttribute("lesson", lesson);
        return "lesson";
    }

    @PostMapping("/{lessonId}/createTask")
    public String addHomework(@PathVariable Long lessonId, Task task) {
        lessonService.createTask(lessonId, task);
        return "redirect:/lesson/" + lessonId;
    }

    @PostMapping("/{lessonId}/passHomework")
    public String passHomework(@AuthenticationPrincipal UserSecurity userSecurity,
                               @PathVariable Long lessonId,
                               HomeworkDto homeworkDto,
                               @RequestParam("photo") MultipartFile[] multipart) {
        lessonService.passHomework(lessonId, homeworkDto, multipart, userSecurity.getUser());
        return "redirect:/lesson/" + lessonId;
    }

    @ExceptionHandler({RuntimeException.class})
    public ModelAndView handleException(RuntimeException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);

        modelAndView.setViewName("error");
        return modelAndView;
    }
}
