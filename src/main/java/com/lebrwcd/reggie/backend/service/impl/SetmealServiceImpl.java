package com.lebrwcd.reggie.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.dto.SetmealDTO;
import com.lebrwcd.reggie.backend.entity.Dish;
import com.lebrwcd.reggie.backend.entity.Setmeal;
import com.lebrwcd.reggie.backend.entity.SetmealDish;
import com.lebrwcd.reggie.backend.mapper.CategoryMapper;
import com.lebrwcd.reggie.backend.mapper.SetmealDishMapper;
import com.lebrwcd.reggie.backend.mapper.SetmealMapper;
import com.lebrwcd.reggie.backend.service.SetmealDishService;
import com.lebrwcd.reggie.backend.service.SetmealService;
import com.lebrwcd.reggie.common.R;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ClassName SetmealServiceImpl
 * Description 套餐业务
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/12
 */
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Transactional
    @Override
    public R<String> saveSetmeal(SetmealDTO dto) {

        // 1.先保存套餐 Setmeal
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(dto, setmeal);
        baseMapper.insert(setmeal);

        // 2.再保存套餐菜品信息 SetmealDish
        // 2.1 先获取上面保存的套餐的id
        Setmeal one = lambdaQuery().eq(Setmeal::getCategoryId, dto.getCategoryId())
                .eq(Setmeal::getName, dto.getName()).one();
        Long setMealId = one.getId();
        // 处理setmealDishes
        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(e -> {
            e.setSetmealId(setMealId);
            return e;
        }).collect(Collectors.toList());
        setmealDishService.saveBatch(setmealDishes);

        return R.success("新增套餐成功!");
    }

    @Override
    public R<Page<SetmealDTO>> pageQuery(Long pageSize, Long pageNum, String name) {
        // 1.创建分页对象
        Page<Setmeal> page = new Page<>(pageNum, pageSize);
        Page<SetmealDTO> resultPage = new Page<>();
        // 构造分页查询条件
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        // 按照更新时间降序排序
        wrapper.like(name != null, Setmeal::getName, name)
                .orderByDesc(Setmeal::getUpdateTime)
                .eq(Setmeal::getIsDeleted, 0);
        page = baseMapper.selectPage(page, wrapper);

        // 对象拷贝,原分页中的records是List<Dish> 我们要的是Lish<DishDTO>
        BeanUtils.copyProperties(page, resultPage, "records");
        List<Setmeal> records = page.getRecords();

        // 处理records
        List<SetmealDTO> list = records.stream().map(e -> {
            SetmealDTO dto = new SetmealDTO();
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
    public R<SetmealDTO> getDishById(Long id) {
        Setmeal setmeal = baseMapper.selectById(id);

        // 封装dto
        SetmealDTO setmealDTO = new SetmealDTO();
        BeanUtils.copyProperties(setmeal, setmealDTO);
        // 套餐菜品
        // 根据当前SetmealId查询该套餐包含的菜品
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId,id);
        List<SetmealDish> setmealDishes = setmealDishService.list(wrapper);
        setmealDTO.setSetmealDishes(setmealDishes);
        setmealDTO.setCategoryName(categoryMapper.selectById(setmealDTO.getCategoryId()).getName());
        return R.success(setmealDTO);
    }

    @Override
    public R<String> updateSetmeal(SetmealDTO dto) {
        // 需要更新两张表
        // 1.更新dish表
        baseMapper.updateById(dto);
        // 2.更新dishFlavor表
        // 2.1 需要先删除原先的内容
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, dto.getId());
        setmealDishService.remove(wrapper);
        // 2.2再插入新的
        List<SetmealDish> setmealDishes = dto.getSetmealDishes();
        setmealDishes = setmealDishes.stream().map(e -> {
            e.setSetmealId(dto.getId());
            return e;
        }).collect(Collectors.toList());
        // 批量插入
        setmealDishService.saveBatch(setmealDishes);
        return R.success("更新套餐信息成功");
    }

    @Override
    public R<String> sellStaus(int status, String ids) {
        // 分割ids，如果分割不开，表示单个修改，如果分割的开，表示批量修改
        String[] split = ids.split("\\,");
        if (split.length == 1) {
            // 单个修改
            Setmeal setmeal = baseMapper.selectById(ids);
            setmeal.setStatus(status);
            baseMapper.updateById(setmeal);
        } else if (split.length > 1) {
            // 批量修改
            List<String> idList = Arrays.stream(split).collect(Collectors.toList());
            List<Setmeal> setmealList = baseMapper.selectBatchIds(idList);
            setmealList = setmealList.stream().map(e -> {
                e.setStatus(status);
                return e;
            }).collect(Collectors.toList());
            setmealService.updateBatchById(setmealList);
        }
        return R.success("修改状态成功");
    }

    @Override
    public R<String> deleteSetmeal(String ids) {
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
        return R.success("删除成功");
    }

    @Override
    public R<List<SetmealDTO>> listParams(Long categoryId, Integer status) {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(categoryId != null,Setmeal::getCategoryId,categoryId)
                .eq(status != null,Setmeal::getStatus,status);
        List<Setmeal> setmealList = baseMapper.selectList(wrapper);

        List<SetmealDTO> setmealDTOS = setmealList.stream().map( e -> {
            SetmealDTO dto = new SetmealDTO();
            BeanUtils.copyProperties(e,dto);
            // 查询套餐所含菜品信息
            LambdaQueryWrapper<SetmealDish> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SetmealDish::getSetmealId,e.getId());
            List<SetmealDish> setmealDishes = setmealDishMapper.selectList(queryWrapper);
            dto.setSetmealDishes(setmealDishes);
            return dto;
        }).collect(Collectors.toList());

        return R.success(setmealDTOS);
    }
}
