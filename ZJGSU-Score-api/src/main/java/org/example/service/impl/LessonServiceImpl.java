package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.Lesson;
import org.example.service.LessonService;
import org.example.mapper.LessonMapper;
import org.springframework.stereotype.Service;

/**
* @author 小假
* @description 针对表【Lesson】的数据库操作Service实现
* @createDate 2024-12-23 14:58:45
*/
@Service
public class LessonServiceImpl extends ServiceImpl<LessonMapper, Lesson>
    implements LessonService{

}




