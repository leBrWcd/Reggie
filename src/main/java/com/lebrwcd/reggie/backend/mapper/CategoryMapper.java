package com.lebrwcd.reggie.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.reggie.backend.entity.Category;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lebrwcd
 * @date 2023/1/10
 * @note
 */
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
