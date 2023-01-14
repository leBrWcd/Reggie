package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.dto.DishDTO;
import com.lebrwcd.reggie.backend.entity.Category;
import com.lebrwcd.reggie.backend.entity.Dish;
import com.lebrwcd.reggie.backend.entity.DishFlavor;
import com.lebrwcd.reggie.backend.mapper.CategoryMapper;
import com.lebrwcd.reggie.backend.mapper.DishFlavorMapper;
import com.lebrwcd.reggie.backend.mapper.DishMapper;
import com.lebrwcd.reggie.backend.service.DishService;
import com.lebrwcd.reggie.backend.service.DishflavorService;
import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName DishServiceImpl
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/12
 */
@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishFlavorMapper flavorMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 涉及多张表的新增操作，添加事务控制
     * @param dto 数据传输对象
     * @return
     */
    @Override
    @Transactional
    public R<String> insert(DishDTO dto) {

        // 1、新增菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto,dish);
        baseMapper.insert(dish);
        // 2、新增菜品口味
        Dish entity = lambdaQuery().eq(Dish::getName, dto.getName())
                .eq(Dish::getCategoryId, dto.getCategoryId()).one();
        List<DishFlavor> flavors = dto.getFlavors();
        flavors.stream().forEach( e -> {
            e.setDishId(entity.getId());
            // 可以遍历，一个一个新增
            flavorMapper.insert(e);
        });
        /*
        // 批量插入
        flavors = flavors.stream().map( e -> {
            e.setDishId(entity.getId());
        }).collect(Collectors.toList());
        dishflavorService.saveBatch(flavors);
        */

        return R.success("添加菜品成功!");
    }

    @Override
    public R<Page<DishDTO>> pageQuery(Long pageSize, Long pageNum, String name) {

        // 1.创建分页对象
        Page<Dish> page = new Page<>(pageNum,pageSize);
        Page<DishDTO> resultPage = new Page<>();
        // 构造分页查询条件
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        // 按照更新时间降序排序
        wrapper.eq(name != null,Dish::getName,name)
                .orderByDesc(Dish::getUpdateTime);
        page = baseMapper.selectPage(page,wrapper);

        // 对象拷贝,原分页中的records是List<Dish> 我们要的是Lish<DishDTO>
        BeanUtils.copyProperties(page,resultPage,"records");
        List<Dish> records = page.getRecords();

        // 处理records
        List<DishDTO> list = records.stream().map( e -> {
            DishDTO dto = new DishDTO();
            BeanUtils.copyProperties(e,dto);
            Long categoryId = e.getCategoryId();
            String categoryName = categoryMapper.selectById(categoryId).getName();
            dto.setCategoryName(categoryName);
            return dto;
        }).collect(Collectors.toList());
        resultPage.setRecords(list);

        if (resultPage.getTotal() >= 1L) {
            return R.success(resultPage);
        }
        return R.error("分页查询失败！");
    }


}
