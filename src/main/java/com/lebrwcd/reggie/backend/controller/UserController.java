package com.lebrwcd.reggie.backend.controller;

import com.lebrwcd.reggie.backend.dto.UserDTO;
import com.lebrwcd.reggie.backend.entity.User;
import com.lebrwcd.reggie.backend.service.UserService;
import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * ClassName UserController
 * Description 用户控制器
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/17
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param user
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<String> login(@RequestBody UserDTO user, HttpSession session) {
        log.info("登录的用户信息： {}",user);
        return userService.login(user,session);
    }

    /**
     * 模拟发送短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user) {
        log.info("手机号 ： {}" ,user.getPhone());
        return userService.sendMessage(user.getPhone());
    }



}
