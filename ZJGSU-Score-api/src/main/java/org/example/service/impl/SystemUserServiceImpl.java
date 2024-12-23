package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.SystemUser;
import org.example.service.SystemUserService;
import org.example.mapper.SystemUserMapper;
import org.springframework.stereotype.Service;

/**
* @author 小假
* @description 针对表【System_User】的数据库操作Service实现
* @createDate 2024-12-23 14:58:51
*/
@Service
public class SystemUserServiceImpl extends ServiceImpl<SystemUserMapper, SystemUser>
    implements SystemUserService{

}




