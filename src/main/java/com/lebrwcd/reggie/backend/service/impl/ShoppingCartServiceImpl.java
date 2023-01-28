package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/26
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.dto.ShoppingCartUpdateDTO;
import com.lebrwcd.reggie.backend.entity.ShoppingCart;
import com.lebrwcd.reggie.backend.mapper.ShoppingCartMapper;
import com.lebrwcd.reggie.backend.service.ShoppingCartService;
import com.lebrwcd.reggie.common.R;
import com.lebrwcd.reggie.common.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * ClassName ShopingCartServiceImpl
 * Description 购物车业务实现
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/26
 */
@Service
@Slf4j
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCartMapper, ShoppingCart> implements ShoppingCartService {

    @Override
    public R<String> addShopping(ShoppingCart shoppingCart) {
        log.info("添加购物车信息中... {}", shoppingCart.toString());
        // 获取用户id
        Long userId = BaseContext.get();
        shoppingCart.setUserId(userId);
        // 先判断购物车中某用户是否已经添加了该菜品，如果添加了，直接数量 + 1
        ShoppingCart one = lambdaQuery().eq(ShoppingCart::getUserId, userId)
                .eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId())
                .eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId())
                .one();
        if (one == null) {
            // 购物车没有
            baseMapper.insert(shoppingCart);
            return R.success("添加购物车信息成功！");
        }
        // 走到这表示购物车中有该菜品。num + 1
        baseMapper.addNum(shoppingCart);
        return R.success("修改购物车信息成功!");
    }

    @Override
    public R<List<ShoppingCart>> getListByUser() {
        // 1.获取当前用户
        Long userId = BaseContext.get();
        // 2.获取该用户的购物车信息
        List<ShoppingCart> list = lambdaQuery().eq(ShoppingCart::getUserId, userId).list();
        if (!list.isEmpty()) {
            return R.success(list);
        }
        return null;
    }

    @Override
    public R<String> sub(ShoppingCartUpdateDTO dto) {
        // 1.获取当前用户
        Long userId = BaseContext.get();
        // 调用该方法说明购物车商品信息减少,直接删除对应的信息
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dto.getDishId() != null, ShoppingCart::getDishId, dto.getDishId())
                .eq(dto.getSetmealId() != null, ShoppingCart::getSetmealId, dto.getSetmealId())
                .eq(ShoppingCart::getUserId, userId);
        ShoppingCart shoppingCart = baseMapper.selectOne(wrapper);
        Integer number = shoppingCart.getNumber();
        if (number > 1) {
            shoppingCart.setNumber(number - 1);
            int update = baseMapper.updateById(shoppingCart);
            if (update > 0) {
                return R.success("修改购物车信息成功");
            }
        } else if (number == 1) {
            int delete = baseMapper.delete(wrapper);
            if (delete > 0) {
                return R.success("修改购物车信息成功");
            }
        }
        return R.error("修改购物车信息错误!");
    }

    @Override
    public R<String> addNum(Map map) {
        ShoppingCart shoppingCart = new ShoppingCart();
        BeanUtils.copyProperties(map,shoppingCart);
        // id处理
        String id = (String) map.get("id");
        Long shoppingCartId = Long.valueOf(id);
        shoppingCart.setId(shoppingCartId);
        shoppingCart.setName((String) map.get("name"));
        baseMapper.addNum(shoppingCart);
        return R.success("更新购物车商品信息成功!");
    }
}
