package com.lebrwcd.reggie.backend.controller;/**
 * @author lebrwcd
 * @date 2023/1/5
 * @note
 */

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.reggie.backend.dto.EmployAddDTO;
import com.lebrwcd.reggie.backend.dto.EmployUpdateDTO;
import com.lebrwcd.reggie.backend.dto.LoginFormDTO;
import com.lebrwcd.reggie.backend.entity.Employee;
import com.lebrwcd.reggie.backend.service.EmployeeService;
import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable("id") Long id) {

        log.info("根据id查询员工信息，id : {}",id);
        Employee employee = employeeService.getById(id);
        // 脱敏处理
        employee.setPassword("*******");
        return R.success(employee);

    }


    @PutMapping()
    public R<String> update(HttpServletRequest request,@RequestBody EmployUpdateDTO dto) {
        log.info("修改的员工信息为: {}",dto.toString());
        // 1.判断是修改状态还是编辑员工信息
        // 1.1先查询员工的状态与前端传过来的状态是否一致，不一致表示需要修改状态，一致编辑员工信息
        Employee employee = employeeService.getById(dto.getId());
        if (employee.getStatus().equals(dto.getStatus())) {
            return employeeService.editEmployee(request,dto);
        }
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
