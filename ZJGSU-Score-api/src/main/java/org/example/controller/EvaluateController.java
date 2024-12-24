package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.example.domain.Evaluate;
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
        UpdateWrapper.eq(Evaluate::getUid,evaluate.getUid());
        return evaluateService.update(evaluate,UpdateWrapper);
    }
    @RequestMapping("/list")
    public List<Evaluate> list() {
        return evaluateService.list();
    }
}