package com.lebrwcd.reggie.backend.dto;/**
 * @author lebrwcd
 * @date 2023/1/7
 * @note
 */

import lombok.Data;
import lombok.ToString;

/**
 * ClassName EmployStatusDTO
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/7
 */
@Data
@ToString
public class EmployUpdateDTO {

    private Long id;

    private Integer status;

    private String username;

    private String name;
    
    private String phone;

    private String sex;

    private String idNumber;
}
