package com.lebrwcd.reggie.backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.reggie.backend.dto.SetmealDTO;
import com.lebrwcd.reggie.backend.service.SetmealService;
import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ClassName SetmealController
 * Description 套餐管理控制器
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/12
 */
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;
/*

    */
    /**
     * 删除套餐 - 批量删除/单个删除
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> deleteSetmeal(@RequestParam("ids") String ids) {
        log.info("删除的菜品id为：{}",ids);
        return setmealService.deleteSetmeal(ids);
    }


    /**
     * @param status 待修改的status 0：停售， 1：启售  批量修改 / 单个修改
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> sellAndNotSell(@PathVariable("status") int status,
                                    @RequestParam("ids") String ids) {
        log.info("起售停售 接口信息：status: {},ids: {}",status,ids);
        return setmealService.sellStaus(status,ids);
    }


    /***
     * 修改套餐信息
     * @param dto
     * @return
     */
    @PutMapping
    public R<String> updateSetmeal(@RequestBody SetmealDTO dto) {
        log.info("修改的套餐信息为: {}",dto.toString());
        return setmealService.updateSetmeal(dto);
    }


    /**
     * 获取套餐信息，做数据回显
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<SetmealDTO> getById(@PathVariable("id") Long id) {
        log.info("查询的套餐信息id : {}" ,id);
        return setmealService.getDishById(id);
    }

    /**
     * 分页查询
     * @param pageSize
     * @param pageNum
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<SetmealDTO>> pageQuery(@RequestParam("pageSize") Long pageSize,
                                         @RequestParam("page") Long pageNum,
                                         @RequestParam(required = false) String name) {
        log.info("套餐管理分页查询 参数 : size {} , num {} ,name {}",pageSize,pageNum,name);
        return setmealService.pageQuery(pageSize,pageNum,name);
    }


    /**
     * 新增套餐
     * @param dto
     * @return
     */
    @PostMapping
    public R<String> saveSetmeal(@RequestBody SetmealDTO dto) {

        log.info("新增套餐信息： {}" ,dto.toString());
        return setmealService.saveSetmeal(dto);
    }

}
