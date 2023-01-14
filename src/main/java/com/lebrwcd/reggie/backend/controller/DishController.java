package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.reggie.backend.dto.DishDTO;
import com.lebrwcd.reggie.backend.entity.Dish;
import com.lebrwcd.reggie.backend.entity.DishFlavor;
import com.lebrwcd.reggie.backend.service.DishService;
import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName DishController
 * Description 菜品控制器
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/12
 */
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @GetMapping("/page")
    public R<Page<DishDTO>> pageQuery(@RequestParam("pageSize") Long pageSize,
                                   @RequestParam("page") Long pageNum,
                                   @RequestParam(required = false) String name) {

        log.info("分页查询参数: 分页大小 {}，分页页码 {}，分页查询名称 {}",pageSize,pageNum,name );
        return dishService.pageQuery(pageSize,pageNum,name);
    }

    @PostMapping
    public R<String> add(@RequestBody DishDTO dto) {

        log.info("添加的菜品信息： {},{}",dto.toString());
        return dishService.insert(dto);
    }

}
