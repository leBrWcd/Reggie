package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.reggie.backend.dto.DishDTO;
import com.lebrwcd.reggie.backend.entity.Dish;
import com.lebrwcd.reggie.backend.service.DishService;
import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * 列表查询
     * @param categoryId
     * @param name
     * @return
     */
    @GetMapping("/list")
    public R<List<Dish>> list(@RequestParam(required = false) Long categoryId,
                              @RequestParam(required = false) String name) {

        log.info("菜品查询列表：categoryId : {} , name = {}",categoryId,name);
        return dishService.listByParam(categoryId,name);

    }

    /**
     * 删除菜品 - 批量删除/单个删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteDish(@RequestParam("ids") String ids) {
        log.info("删除的菜品id为：{}",ids);
        return dishService.deleteDish(ids);
    }

    /**
     *
     * @param status 待修改的status 0：停售， 1：启售  批量修改 / 单个修改
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> sellAndNotSell(@PathVariable("status") int status,
                                    @RequestParam("ids") String ids) {
        log.info("起售停售 接口信息：status: {},ids: {}",status,ids);
        return dishService.sellStaus(status,ids);
    }

    /**
     * 修改菜品信息
     * @param dto
     * @return
     */
    @PutMapping
    public R<String> updateDish(@RequestBody DishDTO dto) {
        log.info("修改的菜品信息为: {}",dto.toString());
        return dishService.updateDish(dto);
    }

    /**
     * 获取菜品信息，做数据回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDTO> getById(@PathVariable("id") Long id) {
        log.info("查询的菜品信息id : {}" ,id);
        return dishService.getDishById(id);
    }

    /**
     * 分页查询
     * @param pageSize 分页大小
     * @param pageNum 当前页码
     * @param name  菜品名称
     * @return  分页结果
     */
    @GetMapping("/page")
    public R<Page<DishDTO>> pageQuery(@RequestParam("pageSize") Long pageSize,
                                   @RequestParam("page") Long pageNum,
                                   @RequestParam(required = false) String name) {

        log.info("分页查询参数: 分页大小 {}，分页页码 {}，分页查询名称 {}",pageSize,pageNum,name );
        return dishService.pageQuery(pageSize,pageNum,name);
    }

    /**
     * 添加菜品
     * @param dto 数据传输对象
     * @return 添加结果
     */
    @PostMapping
    public R<String> add(@RequestBody DishDTO dto) {

        log.info("添加的菜品信息： {},{}",dto.toString());
        return dishService.insert(dto);
    }

}
