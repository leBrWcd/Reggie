package com.lebrwcd.reggie.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.reggie.backend.dto.OrderSubmitDTO;
import com.lebrwcd.reggie.backend.entity.Orders;
import com.lebrwcd.reggie.common.R;

/**
 * @author lebrwcd
 * @date 2023/1/16
 * @note
 */
public interface OrderService extends IService<Orders> {
    R<String> submitOrder(OrderSubmitDTO order);
}
