package com.lebrwcd.reggie.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.reggie.backend.dto.CategoryUpdateDTO;
import com.lebrwcd.reggie.backend.entity.Category;
import com.lebrwcd.reggie.backend.entity.Employee;
import com.lebrwcd.reggie.common.R;

/**
 * @author lebrwcd
 * @date 2023/1/10
 * @note
 */
public interface CategoryService extends IService<Category> {

    R<String> add(Category category);

    R<Page<Category>> pageQuery(Long pageSize, Long pageNum);

    R<String> updateCategory(CategoryUpdateDTO dto);

    R<String> delete(Long id);
}
