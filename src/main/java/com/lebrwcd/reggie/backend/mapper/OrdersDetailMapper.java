package com.lebrwcd.reggie.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.reggie.backend.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lebrwcd
 * @date 2023/1/28
 * @note
 */
@Mapper
public interface OrdersDetailMapper extends BaseMapper<OrderDetail> {
}
