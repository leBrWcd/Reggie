package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.entity.Dish;
import com.lebrwcd.reggie.backend.mapper.DishMapper;
import com.lebrwcd.reggie.backend.service.DishService;
import org.springframework.stereotype.Service;

/**
 * ClassName DishServiceImpl
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/12
 */
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
