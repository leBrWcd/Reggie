package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/16
 * @note
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.dto.OrderSubmitDTO;
import com.lebrwcd.reggie.backend.entity.*;
import com.lebrwcd.reggie.backend.mapper.AddressBookMapper;
import com.lebrwcd.reggie.backend.mapper.OrderMapper;
import com.lebrwcd.reggie.backend.mapper.OrdersDetailMapper;
import com.lebrwcd.reggie.backend.mapper.UserMapper;
import com.lebrwcd.reggie.backend.service.OrderService;
import com.lebrwcd.reggie.common.R;
import com.lebrwcd.reggie.common.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

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

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AddressBookMapper addressBookMapper;

    @Autowired
    private OrdersDetailMapper ordersDetailMapper;

    @Transactional
    @Override
    public R<String> submitOrder(OrderSubmitDTO dto) {
        // 需要处理两张表

        // 1.订单表
        Orders orders = new Orders();
        BeanUtils.copyProperties(dto, orders);
        // 获得用户信息
        Long userId = BaseContext.get();
        User user = userMapper.selectById(userId);
        orders.setUserId(user.getId());
        orders.setUserName(user.getName());
        orders.setPhone(user.getPhone());

        // 封装其余字段
        AddressBook addressBook = addressBookMapper.selectById(dto.getAddressBookId());
        orders.setAddress(addressBook.getDetail());
        orders.setConsignee(addressBook.getConsignee());
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now().plusSeconds(30L));

        // 获得订单总金额
        List<ShoppingCart> cartData = dto.getCartData();
        // 取得集合中的总数 stream().mapToInt( e -> ...).sum()
        int amount = cartData.stream().mapToInt(e -> (e.getAmount().intValue() * e.getNumber())).sum();
        orders.setAmount(BigDecimal.valueOf(amount));
        baseMapper.insert(orders);

        // 2.订单详情表，购物车中的菜品信息
        // 获取上面添加的订单
        Orders one = lambdaQuery().eq(Orders::getAddressBookId, dto.getAddressBookId())
                .eq(Orders::getUserId, userId)
                .eq(Orders::getAmount, amount)
                .one();
        cartData.forEach(e -> {
            OrderDetail orderDetail = new OrderDetail();
            BeanUtils.copyProperties(e,orderDetail);
            orderDetail.setOrderId(one.getId());
            ordersDetailMapper.insert(orderDetail);
        });
        return R.success("订单添加成功！");
    }
}
