package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/28
 * @note
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.entity.OrderDetail;
import com.lebrwcd.reggie.backend.mapper.OrdersDetailMapper;
import com.lebrwcd.reggie.backend.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * ClassName OrderDetailServiceImpl
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/28
 */
@Service
@Slf4j
public class OrderDetailServiceImpl extends ServiceImpl<OrdersDetailMapper, OrderDetail> implements OrderDetailService {
}
