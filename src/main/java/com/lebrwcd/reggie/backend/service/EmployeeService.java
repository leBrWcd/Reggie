package com.lebrwcd.reggie.backend.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lebrwcd.reggie.backend.dto.EmployAddDTO;
import com.lebrwcd.reggie.backend.dto.EmployUpdateDTO;
import com.lebrwcd.reggie.backend.dto.LoginFormDTO;
import com.lebrwcd.reggie.backend.entity.Employee;
import com.lebrwcd.reggie.common.R;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lebrwcd
 * @date 2023/1/5
 * @note 员工服务接口
 */
public interface EmployeeService extends IService<Employee> {

    R<Employee> login(HttpServletRequest request,LoginFormDTO loginForm);

    R<String> add(HttpServletRequest request, EmployAddDTO dto);

    R<Page<Employee>> pageQuery(Long pageSize, Long pageNum, String name);

    R<String> updateStatus(HttpServletRequest request, EmployUpdateDTO dto);

    R<String> editEmployee(HttpServletRequest request, EmployUpdateDTO dto);
}
