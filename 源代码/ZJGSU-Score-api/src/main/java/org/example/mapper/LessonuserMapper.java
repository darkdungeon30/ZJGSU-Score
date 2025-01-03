package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.domain.Lessonuser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 小假
* @description 针对表【LessonUser】的数据库操作Mapper
* @createDate 2024-12-26 19:07:54
* @Entity org.example.domain.Lessonuser
*/
@Mapper
public interface LessonuserMapper extends BaseMapper<Lessonuser> {

}




