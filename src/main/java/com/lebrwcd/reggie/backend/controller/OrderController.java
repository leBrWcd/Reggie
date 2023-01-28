package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/16
 * @note
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.reggie.backend.dto.OrderSubmitDTO;
import com.lebrwcd.reggie.backend.entity.Orders;
import com.lebrwcd.reggie.backend.service.OrderService;
import com.lebrwcd.reggie.common.R;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * ClassName OrderController
 * Description 订单管理控制器
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/16
 */
@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/submit")
    public R<String> submitOrder(@RequestBody OrderSubmitDTO dto) {
        log.info("订单信息: {}",dto.toString());
        return orderService.submitOrder(dto);
    }

    //http://localhost:8080/order/
    // page?page=1&pageSize=10&number=111&beginTime=2023-01-14 00:00:00&endTime=2023-01-25 23:59:59
    @GetMapping("/page")
    public R<Page<Orders>> pageQuery(@RequestParam("page") Long pageNum,
                                     @RequestParam("pageSize") Long pageSize,
                                     @RequestParam(value = "number" , required = false) Long number,
                                     @RequestParam(value = "beginTime",required = false) LocalDateTime beginTime,
                                     @RequestParam(value = "endTime",required = false) LocalDateTime endTime) {
        return null;
    }



}
