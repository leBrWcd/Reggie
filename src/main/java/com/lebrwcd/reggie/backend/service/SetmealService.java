package com.lebrwcd.reggie.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.reggie.backend.dto.SetmealDTO;
import com.lebrwcd.reggie.backend.entity.Setmeal;
import com.lebrwcd.reggie.common.R;

import java.util.List;

/**
 * @author lebrwcd
 * @date 2023/1/12
 * @note
 */
public interface SetmealService extends IService<Setmeal> {

    R<String> saveSetmeal(SetmealDTO dto);

    R<Page<SetmealDTO>> pageQuery(Long pageSize, Long pageNum, String name);

    R<SetmealDTO> getDishById(Long id);

    R<String> updateSetmeal(SetmealDTO dto);

    R<String> sellStaus(int status, String ids);

    R<String> deleteSetmeal(String ids);

    R<List<SetmealDTO>> listParams(Long categoryId, Integer status);
}
