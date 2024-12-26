package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.example.domain.Lesson;
import org.example.domain.Lessonuser;
import org.example.service.LessonuserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/lessonuser")
public class LessonuserController {
    @Resource
    private LessonuserService lessonuserService;
    @RequestMapping("/create")
    public boolean create(@RequestBody Lessonuser lessonuser) {
        return lessonuserService.save(lessonuser);
    }
    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable int id) {
        return lessonuserService.removeById(id);
    }
    @RequestMapping("/update")
    public boolean update(@RequestBody Lessonuser lessonuser) {
        LambdaUpdateWrapper<Lessonuser> UpdateWrapper = new LambdaUpdateWrapper<>();
        UpdateWrapper.eq(Lessonuser::getLuid,lessonuser.getLuid());
        return lessonuserService.update(lessonuser,UpdateWrapper);
    }
    @RequestMapping("/list")
    public List<Lessonuser> list() {
        return lessonuserService.list();
    }
    @RequestMapping("/info/{id}")
    public Lessonuser info(@PathVariable int id) {
        return lessonuserService.getById(id);
    }
}
