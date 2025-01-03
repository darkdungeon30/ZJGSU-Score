package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
import java.util.stream.Collectors;

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
    @RequestMapping("/lesson/{id}")
    public int[] lesson(@PathVariable int id) {
        LambdaQueryWrapper<Lessonuser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Lessonuser::getUid,id);
        List<Lessonuser> llist = lessonuserService.list(lambdaQueryWrapper);
        List<Integer> lidList = llist.stream()
                .map(Lessonuser::getLid) // 假设Lessonuser类有一个getLid()方法
                .collect(Collectors.toList());
        // 将列表转换为数组
        int[] lidArray = lidList.stream().mapToInt(Integer::intValue).toArray();
        return lidArray;
    }
}
