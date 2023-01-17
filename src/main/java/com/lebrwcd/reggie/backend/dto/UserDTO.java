package com.lebrwcd.reggie.backend.dto;/**
 * @author lebrwcd
 * @date 2023/1/17
 * @note
 */

import lombok.Data;
import lombok.ToString;

/**
 * ClassName UserDTO
 * Description 用户登录数据传输对象
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/17
 */
@Data
@ToString
public class UserDTO {

    private String phone;

    private Integer code;

}

