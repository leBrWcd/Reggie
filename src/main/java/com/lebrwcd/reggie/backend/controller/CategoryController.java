package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/10
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.reggie.backend.dto.CategoryUpdateDTO;
import com.lebrwcd.reggie.backend.entity.Category;
import com.lebrwcd.reggie.backend.entity.Employee;
import com.lebrwcd.reggie.backend.service.CategoryService;
import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ClassName CategoryController
 * Description 分类管理控制器
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/10
 */
@RestController
@Slf4j
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public R<List<Category>> getCategoryList(Integer type) {
        log.info("菜品分类type ==========={}",type);
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getType,type);
        List<Category> list = categoryService.list(wrapper);
        return R.success(list);
    }

    @DeleteMapping
    public R<String> delete(Long id) {
        log.info("删除分类的id = {}",id);
        return categoryService.delete(id);
    }


    @PutMapping
    public R<String> update(@RequestBody CategoryUpdateDTO dto) {
        log.info("修改的分类对象: {}",dto.toString());
        return categoryService.updateCategory(dto);
    }

    /**
     * 分页查询
     * @param pageSize 每页显示条数
     * @param pageNum 当前页码
     * @return 分页数据
     */
    @GetMapping("/page")
    public R<Page<Category>> page(@RequestParam("pageSize") Long pageSize,
                                  @RequestParam("page") Long pageNum ) {

        log.info("分页参数 pageSize = {},pageNum = {}",pageSize,pageNum);
        return categoryService.pageQuery(pageSize,pageNum);
    }

    /**
     * 新增菜品分类(type = 1)和新增套餐分类(type = 2)
     * @param category
     * @return
     */
    @PostMapping()
    public R<String> add(@RequestBody Category category) {
        return categoryService.add(category);
    }






}
