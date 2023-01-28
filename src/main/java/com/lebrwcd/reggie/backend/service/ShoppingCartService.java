package com.lebrwcd.reggie.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.reggie.backend.dto.ShoppingCartUpdateDTO;
import com.lebrwcd.reggie.backend.entity.ShoppingCart;
import com.lebrwcd.reggie.common.R;

import java.util.List;
import java.util.Map;

/**
 * @author lebrwcd
 * @date 2023/1/26
 * @note
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    R<String> addShopping(ShoppingCart shoppingCart);

    R<List<ShoppingCart>> getListByUser();

    R<String> sub(ShoppingCartUpdateDTO dto);

    R<String> addNum(Map map);
}
