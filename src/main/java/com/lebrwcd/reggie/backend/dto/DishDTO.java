package com.lebrwcd.reggie.backend.dto;/**
 * @author lebrwcd
 * @date 2023/1/14
 * @note
 */

import com.lebrwcd.reggie.backend.entity.Dish;
import com.lebrwcd.reggie.backend.entity.DishFlavor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName DishDTO
 * Description 菜品数据传输对象
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/14
 */
@Data
@ToString
@NoArgsConstructor
public class DishDTO extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;

}
