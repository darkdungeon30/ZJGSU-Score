package org.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import jakarta.annotation.Resource;
import org.example.DTO.Response;
import org.example.domain.SystemUser;
import org.example.service.SystemUserService;
import org.example.utils.JwtUtils;
import org.example.utils.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PostMapping("/login")
    public Result login(@RequestBody SystemUser systemUser){
        LambdaQueryWrapper<SystemUser> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(SystemUser::getUaccount,systemUser.getUaccount());
        lambdaQueryWrapper.eq(SystemUser::getUpassword,systemUser.getUpassword());
        SystemUser u = new SystemUser();
        if (!systemUserService.list(lambdaQueryWrapper).isEmpty()){
            u = systemUserService.list(lambdaQueryWrapper).getFirst();
        }
        else {
            u = null;
        }

        // 如果登陆成功，生成令牌，下发令牌

        if (u != null){
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",u.getUid());
            claims.put("account",u.getUaccount());
            claims.put("password",u.getUpassword());
            claims.put("name",u.getUname());
            claims.put("type",u.getUtype());
            String jwtcode = JwtUtils.generateJwt(claims);
            return Result.success(jwtcode);
        }
        // 登陆失败，返回错误信息
        else{
            return Result.error("用户名或密码失败~");
        }
    }
}
