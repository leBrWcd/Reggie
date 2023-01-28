package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/26
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lebrwcd.reggie.backend.dto.ShoppingCartUpdateDTO;
import com.lebrwcd.reggie.backend.entity.ShoppingCart;
import com.lebrwcd.reggie.backend.service.ShoppingCartService;
import com.lebrwcd.reggie.common.R;
import com.lebrwcd.reggie.common.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * ClassName ShopingCartController
 * Description 购物车控制器
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/26
 */
@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shopingCartService;

    @DeleteMapping("/clean")
    public R<String> clean() {
        // 获取当前用户id、
        Long userId = BaseContext.get();
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId,userId);
        shopingCartService.remove(wrapper);
        return R.success("清空购物车成功！");
    }

    @PostMapping("/addNum")
    public R<String> addNum(@RequestBody Map map) {
        /**map :
         * {
         *  shoppingCart=
         *      {id=1619242447227969538, name=毛氏红烧肉, userId=1615332488303984642,
         * dishId=1397850140982161409, setmealId=null, dishFlavor=不要香菜,中辣,
         * number=1, amount=68, image=0a3b3288-3446-4420-bbff-f263d0c02d8e.jpg, createTime=null
         *      }
         * }
         */
        log.info("购物车中的菜品数量增加 {}",map.toString() );
        return shopingCartService.addNum((Map) map.get("shoppingCart"));
    }

    @PostMapping("/sub")
    public R<String> updateShoppingCart(@RequestBody ShoppingCartUpdateDTO dto) {
        log.info("修改的购物车信息: {}",dto.toString());
        return shopingCartService.sub(dto);
    }

    /**
     * 获取用户对应的购物车列表
     * @return
     */
    @GetMapping("/list")
    public R<List<ShoppingCart>> getList() {
        return shopingCartService.getListByUser();
    }

    /**
     * 添加购物车信息
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody ShoppingCart shoppingCart) {
        log.info("添加到购物车的菜品信息：{}",shoppingCart.toString());
        return shopingCartService.addShopping(shoppingCart);
    }

}
