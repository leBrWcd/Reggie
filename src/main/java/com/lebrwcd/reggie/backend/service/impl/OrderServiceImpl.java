package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/16
 * @note
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.entity.Orders;
import com.lebrwcd.reggie.backend.mapper.OrderMapper;
import com.lebrwcd.reggie.backend.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ClassName OrderServiceImpl
 * Description 订单业务层
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/16
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Orders> implements OrderService {

}
