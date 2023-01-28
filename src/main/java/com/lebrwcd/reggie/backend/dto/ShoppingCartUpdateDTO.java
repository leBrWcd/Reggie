package com.lebrwcd.reggie.backend.dto;/**
 * @author lebrwcd
 * @date 2023/1/28
 * @note
 */

import lombok.Data;
import lombok.ToString;

/**
 * ClassName ShoppingCartUpdateDTO
 * Description 购物车更新DTO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/28
 */
@Data
@ToString
public class ShoppingCartUpdateDTO {

    /**
     * 菜品id
     */

    private Long dishId;
    /**
     * 套餐id
     */
    private Long setmealId;

}
