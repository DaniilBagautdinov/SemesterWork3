package ru.kpfu.itis.bagautdinov.controllers;

import ru.kpfu.itis.bagautdinov.services.FilesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
@RequestMapping("/files")
public class FilesController {
    private final FilesService filesService;

    @GetMapping("/{file-name:.+}")
    public void getFile(@PathVariable("file-name") String fileName, HttpServletResponse response) {
        filesService.addFileToResponse(fileName, response);
    }

    @ExceptionHandler({RuntimeException.class})
    public ModelAndView handleException(RuntimeException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e);

        modelAndView.setViewName("error");
        return modelAndView;
    }
}
