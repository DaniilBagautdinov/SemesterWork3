package ru.kpfu.itis.bagautdinov.controllers;

import ru.kpfu.itis.bagautdinov.dto.HomeworkInfoDto;
import ru.kpfu.itis.bagautdinov.models.Homework;
import ru.kpfu.itis.bagautdinov.security.details.UserSecurity;
import ru.kpfu.itis.bagautdinov.services.HomeworkService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RequiredArgsConstructor
@Controller
@RequestMapping("/homework")
public class HomeworkController {
    private final HomeworkService homeworkService;

    @GetMapping("/{homeworkId}")
    public String getHomework(@AuthenticationPrincipal UserSecurity userSecurity,
                              @PathVariable Long homeworkId, Model model) {
        Homework homework = homeworkService.getHomework(homeworkId, userSecurity.getUser());
        HomeworkInfoDto homeworkInfo = homeworkService.getHomeworkInfo(homeworkId, userSecurity.getUser());
        model.addAttribute("homeworkInfo", homeworkInfo);
        model.addAttribute("homework", homework);
        return "homework";
    }

    @PostMapping("/{homeworkId}/checkHomework")
    public String checkHomework(@PathVariable Long homeworkId, @RequestParam Integer score) {
        homeworkService.checkHomework(homeworkId, score);
        return "redirect:/homework/" + homeworkId;
    }

    @ExceptionHandler({RuntimeException.class})
    public ModelAndView handleException(RuntimeException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);

        modelAndView.setViewName("error");
        return modelAndView;
    }
}
