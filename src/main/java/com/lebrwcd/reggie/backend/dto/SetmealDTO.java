package com.lebrwcd.reggie.backend.dto;/**
 * @author lebrwcd
 * @date 2023/1/16
 * @note
 */

import com.lebrwcd.reggie.backend.entity.Setmeal;
import com.lebrwcd.reggie.backend.entity.SetmealDish;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName SetmealDTO
 * Description 套餐新增数据传输对象
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/16
 */
@Data
@ToString
public class SetmealDTO extends Setmeal {

    /**
     * 套餐菜品
     */
    private List<SetmealDish> setmealDishes = new ArrayList<>();

    /**
     * 套餐菜品分类名称
     */
    private String categoryName;
}
