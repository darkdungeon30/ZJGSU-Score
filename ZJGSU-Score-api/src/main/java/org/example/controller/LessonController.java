package org.example.controller;

import jakarta.annotation.Resource;
import org.example.service.LessonService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/lesson")
public class LessonController {
    @Resource
    private LessonService lessonService;

    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable int id) {
        return lessonService.removeById(id);
    }

}
