package com.lebrwcd.reggie.backend.dto;/**
 * @author lebrwcd
 * @date 2023/1/6
 * @note
 */

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * ClassName EmployAddDTO
 * Description 新增员工数据传输对象
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/6
 */
@Data
@NoArgsConstructor
@ToString
public class EmployAddDTO {

    private String username;

    private String name;

    private String phone;

    private String sex;

    private String idNumber;

}
