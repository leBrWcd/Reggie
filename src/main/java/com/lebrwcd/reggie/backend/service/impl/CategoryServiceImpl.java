package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/10
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.dto.CategoryUpdateDTO;
import com.lebrwcd.reggie.backend.entity.Category;
import com.lebrwcd.reggie.backend.entity.Dish;
import com.lebrwcd.reggie.backend.entity.Employee;
import com.lebrwcd.reggie.backend.entity.Setmeal;
import com.lebrwcd.reggie.backend.mapper.CategoryMapper;
import com.lebrwcd.reggie.backend.mapper.DishMapper;
import com.lebrwcd.reggie.backend.mapper.SetmealMapper;
import com.lebrwcd.reggie.backend.service.CategoryService;
import com.lebrwcd.reggie.common.R;
import com.lebrwcd.reggie.common.exec.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName CategoryServiceImpl
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/10
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private DishMapper dishMapper;

    @Override
    public R<String> add(Category category) {
        int insert = baseMapper.insert(category);
        if (insert == 0) {
            // 插入失败
            return R.error("新增失败！");
        }
        return R.success("新增成功");
    }

    @Override
    public R<Page<Category>> pageQuery(Long pageSize, Long pageNum) {
        // 1.创建分页对象
        Page<Category> categoryPage = new Page<>(pageNum,pageSize);
        // 2.按照排序自动降序排序
        categoryPage = baseMapper.selectPage(categoryPage,new LambdaQueryWrapper<Category>().orderByDesc(Category::getSort));
        if (categoryPage.getTotal() >= 1L) {
            return R.success(categoryPage);
        }
        return R.error(null);
    }

    @Override
    public R<String> updateCategory(CategoryUpdateDTO dto) {

        LambdaUpdateWrapper<Category> wrapper = new LambdaUpdateWrapper<>();
        // 1.更新id查询当前分类
        Category category = baseMapper.selectById(dto.getId());
        // 2.更新操作
        category.setName(dto.getName());
        category.setSort(dto.getSort());
        int update = baseMapper.updateById(category);
        if (update == 0) {
            return R.error("操作失败！");
        }
        return R.success("操作成功！");
    }

    @Override
    public R<String> delete(Long id) {
        // 1.判断该分类是否关联了某个套餐
        LambdaQueryWrapper<Setmeal> wrapper1 = new LambdaQueryWrapper<>();
        wrapper1.eq(Setmeal::getCategoryId,id);
        Integer count1 = setmealMapper.selectCount(wrapper1);
        if (count1 > 0) {
            // 有关联
            throw new CustomerException("该分类关联了套餐，无法删除!");
        }

        // 2.判断该分类是否关联了某个菜品
        LambdaQueryWrapper<Dish> wrapper2 = new LambdaQueryWrapper<>();
        wrapper2.eq(Dish::getCategoryId,id);
        Integer count2 = dishMapper.selectCount(wrapper2);
        if (count2> 0) {
            // 有关联
            throw new CustomerException("该分类关联了菜品，无法删除!");
        }
        // 可以删除
        int delete = baseMapper.deleteById(id);
        if (delete > 0) {
            return R.success("删除分类成功");
        }
        return R.error("删除分类失败");
    }
}
