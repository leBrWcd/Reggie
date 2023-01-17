package com.lebrwcd.reggie.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.reggie.backend.dto.UserDTO;
import com.lebrwcd.reggie.backend.entity.User;
import com.lebrwcd.reggie.common.R;

import javax.servlet.http.HttpSession;

/**
 * @author lebrwcd
 * @date 2023/1/17
 * @note
 */
public interface UserService extends IService<User> {
    R<String> sendMessage(String phone);

    R<String> login(UserDTO user, HttpSession session);
}
