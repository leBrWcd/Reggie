package com.lebrwcd.reggie.backend.dto;/**
 * @author lebrwcd
 * @date 2023/1/28
 * @note
 */

import com.lebrwcd.reggie.backend.entity.ShoppingCart;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * ClassName OrderSubmitDTO
 * Description 订单提交DTO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/28
 */
@Data
@ToString
public class OrderSubmitDTO {

    //地址id
    private Long addressBookId;

    //支付方式 1微信，2支付宝
    private Integer payMethod;

    //备注
    private String remark;

    private List<ShoppingCart> cartData;


}
