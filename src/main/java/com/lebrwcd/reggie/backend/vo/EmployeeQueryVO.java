package com.lebrwcd.reggie.backend.vo;/**
 * @author lebrwcd
 * @date 2023/1/7
 * @note
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ClassName EmployeeQueryVO
 * Description 员工分页查询展示对象
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/7
 */
@Data
@NoArgsConstructor
@ToString
public class EmployeeQueryVO {

    private Long id;

    private String username;

    private String name;

    private String phone;

    private Integer status;

}
