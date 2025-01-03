package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.example.domain.Evaluate;
import org.example.domain.Lesson;
import org.example.domain.SystemUser;
import org.example.service.EvaluateService;
import org.example.service.SystemUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/evaluate")
public class EvaluateController {
    @Resource
    private EvaluateService evaluateService;

    @RequestMapping("/create")
    public boolean create(@RequestBody Evaluate evaluate) {
        return evaluateService.save(evaluate);
    }
    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable int id) {
        return evaluateService.removeById(id);
    }
    @RequestMapping("/update")
    public boolean update(@RequestBody Evaluate evaluate) {
        LambdaUpdateWrapper<Evaluate> UpdateWrapper = new LambdaUpdateWrapper<>();
        UpdateWrapper.eq(Evaluate::getEid,evaluate.getEid());
        return evaluateService.update(evaluate,UpdateWrapper);
    }
    @RequestMapping("/list")
    public List<Evaluate> list() {
        return evaluateService.list();
    }
    @RequestMapping("/get_by_lid/{id}")
    public List<Evaluate> get_by_lid(@PathVariable int id) {
        LambdaQueryWrapper<Evaluate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Evaluate::getLid,id);
        return evaluateService.list(lambdaQueryWrapper);
    }
    @RequestMapping("/get_by_uid/{id}")
    public List<Evaluate> get_by_uid(@PathVariable int id) {
        LambdaQueryWrapper<Evaluate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Evaluate::getUid,id);
        return evaluateService.list(lambdaQueryWrapper);
    }
    @RequestMapping("/search")
    public List<Evaluate> get_by_uid(@RequestBody Evaluate evaluate) {
        LambdaQueryWrapper<Evaluate> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Evaluate::getUid,evaluate.getUid());
        lambdaQueryWrapper.eq(Evaluate::getLid,evaluate.getLid());
        return evaluateService.list(lambdaQueryWrapper);
    }
    @RequestMapping("/info/{id}")
    public Evaluate info(@PathVariable int id) {
        return evaluateService.getById(id);
    }
}
