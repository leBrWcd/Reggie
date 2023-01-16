package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/16
 * @note
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.entity.SetmealDish;
import com.lebrwcd.reggie.backend.mapper.SetmealDishMapper;
import com.lebrwcd.reggie.backend.service.SetmealDishService;
import org.springframework.stereotype.Service;

/**
 * ClassName SetmealDishServiceImpl
 * Description 套餐菜品关系业务实现
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/16
 */
@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {
}
