package com.lebrwcd.reggie.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.reggie.backend.dto.DishDTO;
import com.lebrwcd.reggie.backend.entity.Dish;
import com.lebrwcd.reggie.common.R;

/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */
public interface DishService extends IService<Dish> {
    R<String> insert(DishDTO dish);

    R<Page<DishDTO>> pageQuery(Long pageSize, Long pageNum, String name);
}
