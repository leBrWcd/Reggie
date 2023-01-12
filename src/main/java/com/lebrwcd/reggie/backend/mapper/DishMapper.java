package com.lebrwcd.reggie.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.reggie.backend.entity.Dish;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */
@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
