package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.example.DTO.Response;
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

    @RequestMapping("/register")
    public Response register(@RequestBody SystemUser systemUser) {
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemUser::getUaccount,systemUser.getUaccount());
        if (systemUserService.list(lambdaQueryWrapper).isEmpty()) {
            // 账号不存在，继续注册逻辑
            boolean isSaved = systemUserService.save(systemUser);
            if (isSaved) {
                return new Response(200, "注册成功", systemUser); // 假设Response构造函数接受消息和状态码
            } else {
                return new Response(500, "注册失败", null); // 假设Response构造函数接受消息和状态码
            }
        } else {
            // 账号存在，返回错误码401
            return new Response(401, "账号已存在", null); // 假设Response构造函数接受消息和状态码
        }
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
