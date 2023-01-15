package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.dto.DishDTO;
import com.lebrwcd.reggie.backend.entity.Dish;
import com.lebrwcd.reggie.backend.entity.DishFlavor;
import com.lebrwcd.reggie.backend.mapper.CategoryMapper;
import com.lebrwcd.reggie.backend.mapper.DishFlavorMapper;
import com.lebrwcd.reggie.backend.mapper.DishMapper;
import com.lebrwcd.reggie.backend.service.DishService;
import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName DishServiceImpl
 * Description 菜品管理业务
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

    @Autowired
    private DishService dishService;

    /**
     * 涉及多张表的新增操作，添加事务控制
     *
     * @param dto 数据传输对象
     * @return
     */
    @Override
    @Transactional
    public R<String> insert(DishDTO dto) {

        // 1、新增菜品
        Dish dish = new Dish();
        BeanUtils.copyProperties(dto, dish);
        baseMapper.insert(dish);
        // 2、新增菜品口味
        Dish entity = lambdaQuery().eq(Dish::getName, dto.getName())
                .eq(Dish::getCategoryId, dto.getCategoryId()).one();
        List<DishFlavor> flavors = dto.getFlavors();
        flavors.stream().forEach(e -> {
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
        Page<Dish> page = new Page<>(pageNum, pageSize);
        Page<DishDTO> resultPage = new Page<>();
        // 构造分页查询条件
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        // 按照更新时间降序排序
        wrapper.eq(name != null, Dish::getName, name)
                .orderByDesc(Dish::getUpdateTime)
                .eq(Dish::getIsDeleted,0);
        page = baseMapper.selectPage(page, wrapper);

        // 对象拷贝,原分页中的records是List<Dish> 我们要的是Lish<DishDTO>
        BeanUtils.copyProperties(page, resultPage, "records");
        List<Dish> records = page.getRecords();

        // 处理records
        List<DishDTO> list = records.stream().map(e -> {
            DishDTO dto = new DishDTO();
            BeanUtils.copyProperties(e, dto);
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

    @Override
    public R<DishDTO> getDishById(Long id) {
        // 根据id查询dish
        Dish dish = baseMapper.selectById(id);
        // 封装dishDTO
        DishDTO dto = new DishDTO();
        BeanUtils.copyProperties(dish, dto);
        // 根据当前dishId查询dish的口味组合
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, id);
        List<DishFlavor> flavors = flavorMapper.selectList(wrapper);
        dto.setFlavors(flavors);
        dto.setCategoryName(categoryMapper.selectById(dto.getCategoryId()).getName());
        return R.success(dto);
    }

    @Transactional
    @Override
    public R<String> updateDish(DishDTO dto) {
        // 需要更新两张表
        // 1.更新dish表
        baseMapper.updateById(dto);
        // 2.更新dishFlavor表
        // 2.1 需要先删除原先的内容
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dto.getId());
        flavorMapper.delete(wrapper);
        // 2.2再插入新的
        List<DishFlavor> flavors = dto.getFlavors();
        flavors.stream().forEach(e -> {
            e.setDishId(dto.getId());
            // 可以遍历，一个一个新增
            flavorMapper.insert(e);
        });
        return R.success("更新菜品信息成功");
    }

    @Override
    public R<String> sellStaus(int status, String ids) {
        // 分割ids，如果分割不开，表示单个修改，如果分割的开，表示批量修改
        String[] split = ids.split("\\,");
        if (split.length == 1) {
            // 单个修改
            Dish dish = baseMapper.selectById(ids);
            dish.setStatus(status);
            baseMapper.updateById(dish);
        } else if (split.length > 1) {
            // 批量修改
            List<String> idList = Arrays.stream(split).collect(Collectors.toList());
            List<Dish> dishes = baseMapper.selectBatchIds(idList);
            dishes = dishes.stream().map( e -> {
                e.setStatus(status);
                return e;
            }).collect(Collectors.toList());
            dishService.updateBatchById(dishes);
        }
        return R.success("修改状态成功");
    }

    @Override
    public R<String> deleteDish(String ids) {
        // 分割ids，如果分割不开，表示单个修改，如果分割的开，表示批量修改
        String[] split = ids.split("\\,");
        if (split.length == 1) {
            // 单个修改
            int delete = baseMapper.deleteById(ids);
            if (delete == 0) {
                return R.error("删除失败!");
            }
        } else if (split.length > 1) {
            // 批量修改
            List<String> idList = Arrays.stream(split).collect(Collectors.toList());
            baseMapper.deleteBatchIds(idList);
        }
        return R.success("修改状态成功");
    }
}
