package com.lebrwcd.reggie.backend.dto;/**
 * @author lebrwcd
 * @date 2023/1/11
 * @note
 */

import lombok.Data;
import lombok.ToString;

/**
 * ClassName CategoryUpdateDTO
 * Description
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/11
 */
@Data
@ToString
public class CategoryUpdateDTO {

    private Long id;
    /**
     * 分类名称
     */
    private String name;

    /**
     * 顺序
     */
    private Integer sort;

}
