package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/5
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lebrwcd.reggie.backend.dto.LoginFormDTO;
import com.lebrwcd.reggie.backend.entity.Employee;
import com.lebrwcd.reggie.backend.service.EmployeeService;
import com.lebrwcd.reggie.common.R;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ClassName EmployeeController
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/5
 */
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 登录
     * @param request 请求对象
     * @param loginForm 登录表单
     * @return R
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody LoginFormDTO loginForm) {

        // 1.密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(loginForm.getPassword().getBytes());
        // 2.根据用户名查询数据库
        String username = loginForm.getUsername();
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,username);
        Employee employee = employeeService.getOne(queryWrapper);
        // 2.1 用户名不存在，返回错误信息
        // TODO 后续将存在的用户加入缓存，不存在的返回空，防止缓存击穿
        if (employee == null) {
            return R.error("该用户不存在!");
        }
        // 2.2 存在，校验密码
        if (!employee.getPassword().equals(password)) {
            return R.error("密码错误，请重新输入!");
        }
        // 3 密码正确，校验用户状态是否为禁用状态
        if (employee.getStatus() != 1) {
            return R.error("员工已被禁用!");
        }
        // 不是禁用，登录成功,获取员工id
        Long id = employee.getId();
        // TODO 后续采用redis，而不采用session的方式
        request.getSession().setAttribute("employeeId",id);

        return R.success(employee);
    }

    /**
     * 退出
     * @param request 请求对象
     * @return string
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {

        request.getSession().removeAttribute("employeeId");
        return R.success("退出系统");
    }


}
