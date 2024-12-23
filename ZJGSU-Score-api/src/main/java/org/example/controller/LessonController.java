package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.example.domain.Lesson;
import org.example.service.LessonService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lesson")
public class LessonController {
    @Resource
    private LessonService lessonService;

    @RequestMapping("/create")
    public boolean create(@RequestBody Lesson lesson) {
        return lessonService.save(lesson);
    }
    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable int id) {
        return lessonService.removeById(id);
    }
    @RequestMapping("/update")
    public boolean update(@RequestBody Lesson lesson) {
        LambdaUpdateWrapper<Lesson> UpdateWrapper = new LambdaUpdateWrapper<>();
        UpdateWrapper.eq(Lesson::getLid,lesson.getLid());
        return lessonService.update(lesson,UpdateWrapper);
    }
    @RequestMapping("/list")
    public List<Lesson> list() {
        return lessonService.list();
    }
}
