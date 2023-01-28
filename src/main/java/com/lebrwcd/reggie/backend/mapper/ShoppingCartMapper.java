package com.lebrwcd.reggie.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.reggie.backend.entity.ShoppingCart;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author lebrwcd
 * @date 2023/1/26
 * @note
 */
@Mapper
public interface ShoppingCartMapper extends BaseMapper<ShoppingCart> {
    void addNum(@Param("map") ShoppingCart shoppingCart);
}
