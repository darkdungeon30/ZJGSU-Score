package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.example.domain.Lesson;
import org.example.domain.SystemUser;
import org.example.service.SystemUserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private SystemUserService systemUserService;

    @RequestMapping("/create")
    public boolean create(@RequestBody SystemUser systemUser) {
        return systemUserService.save(systemUser);
    }
    @RequestMapping("/delete/{id}")
    public boolean delete(@PathVariable int id) {
        return systemUserService.removeById(id);
    }
    @RequestMapping("/update")
    public boolean update(@RequestBody SystemUser systemUser) {
        LambdaUpdateWrapper<SystemUser> UpdateWrapper = new LambdaUpdateWrapper<>();
        UpdateWrapper.eq(SystemUser::getUid,systemUser.getUid());
        return systemUserService.update(systemUser,UpdateWrapper);
    }
    @RequestMapping("/list")
    public List<SystemUser> list() {
        return systemUserService.list();
    }
    @RequestMapping("/login")
    public List<SystemUser> login(@RequestBody SystemUser systemUser) {
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemUser::getUaccount,systemUser.getUaccount());
        lambdaQueryWrapper.eq(SystemUser::getUpassword,systemUser.getUpassword());
        return systemUserService.list(lambdaQueryWrapper);
    }
}
