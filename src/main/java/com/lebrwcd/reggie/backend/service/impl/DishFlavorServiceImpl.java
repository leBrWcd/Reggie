package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/14
 * @note
 */

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.entity.DishFlavor;
import com.lebrwcd.reggie.backend.mapper.DishFlavorMapper;
import com.lebrwcd.reggie.backend.service.DishService;
import com.lebrwcd.reggie.backend.service.DishflavorService;
import org.springframework.stereotype.Service;

/**
 * ClassName DishFlavorServiceImpl
 * Description 菜品口味业务层
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/14
 */
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishflavorService {


}
