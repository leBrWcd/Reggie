package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/5
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.reggie.backend.dto.EmployAddDTO;
import com.lebrwcd.reggie.backend.dto.EmployStatusDTO;
import com.lebrwcd.reggie.backend.dto.LoginFormDTO;
import com.lebrwcd.reggie.backend.entity.Employee;
import com.lebrwcd.reggie.backend.service.EmployeeService;
import com.lebrwcd.reggie.backend.vo.EmployeeQueryVO;
import com.lebrwcd.reggie.common.R;
import com.sun.org.apache.regexp.internal.RE;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * ClassName EmployeeController
 * Description 员工控制器
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


    @PutMapping()
    public R<String> statusHandle(HttpServletRequest request,@RequestBody EmployStatusDTO dto) {
        log.info("修改的员工信息为: {}",dto.toString());
        return employeeService.updateStatus(request,dto);
    }


    /**
     * 分页查询
     * @param pageSize
     * @param pageNum
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(@RequestParam("pageSize") Long pageSize,
                                         @RequestParam("page") Long pageNum,
                                         @RequestParam(value = "name",required = false) String name) {

        log.info("分页参数 pageSize = {},pageNum = {},name = {}",pageSize,pageNum,name);
        return employeeService.pageQuery(pageSize,pageNum,name);
    }

    /**
     * 新增员工
     * @param request 请求对象
     * @param dto 数据传输对象
     * @return 处理结果
     */
    @PostMapping()
    public R<String> addEmployee(HttpServletRequest request,@RequestBody EmployAddDTO dto) {
        log.info("=========添加员工信息: {}",dto.toString());
        return employeeService.add(request,dto);
    }

    /**
     * 登录
     * @param request 请求对象
     * @param loginForm 登录表单
     * @return R
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody LoginFormDTO loginForm) {
        return employeeService.login(request,loginForm);
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
