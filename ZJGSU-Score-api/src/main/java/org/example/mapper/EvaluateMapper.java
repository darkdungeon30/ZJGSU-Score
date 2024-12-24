package org.example.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.example.domain.Evaluate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author 小假
* @description 针对表【Evaluate】的数据库操作Mapper
* @createDate 2024-12-23 21:28:16
* @Entity org.example.domain.Evaluate
*/
@Mapper
public interface EvaluateMapper extends BaseMapper<Evaluate> {

}




