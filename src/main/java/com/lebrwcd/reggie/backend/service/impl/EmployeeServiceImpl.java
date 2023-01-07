package com.lebrwcd.reggie.backend.service.impl;/**
 * @author lebrwcd
 * @date 2023/1/5
 * @note
 */

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.dto.EmployAddDTO;
import com.lebrwcd.reggie.backend.dto.LoginFormDTO;
import com.lebrwcd.reggie.backend.entity.Employee;
import com.lebrwcd.reggie.backend.mapper.EmployeeMapper;
import com.lebrwcd.reggie.backend.service.EmployeeService;
import com.lebrwcd.reggie.backend.vo.EmployeeQueryVO;
import com.lebrwcd.reggie.common.R;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName EmployeeServiceImpl
 * Description 员工服务类
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/5
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {


    @Override
    public R<Employee> login(HttpServletRequest request,LoginFormDTO loginForm) {
        // 1.密码进行md5加密
        String password = DigestUtils.md5DigestAsHex(loginForm.getPassword().getBytes());
        // 2.根据用户名查询数据库
        String username = loginForm.getUsername();
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,username);
        Employee employee = baseMapper.selectOne(queryWrapper);
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

    @Override
    public R<String> add(HttpServletRequest request, EmployAddDTO dto) {
         /* // username唯一约束，先判断用户名是否在数据库中存在
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,dto.getUsername());
        Employee one = employeeService.getOne(queryWrapper);
        if (one != null) {
            // 存在员工，添加失败
            return R.error("该员工已存在，请勿重复添加！");
        }*/
        // 可以添加
        Employee employee = new Employee();
        BeanUtils.copyProperties(dto,employee);
        // 获得当前用户
        Long user = (Long) request.getSession().getAttribute("employeeId");
        employee.setCreateUser(user);
        employee.setUpdateUser(user);
        // 设置初始密码 123456
        String defaultPassword = "123456";
        String password = DigestUtils.md5DigestAsHex(defaultPassword.getBytes());
        employee.setPassword(password);
        // 设置时间
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());

        baseMapper.insert(employee);
        return R.success("添加成功！");
    }

    @Override
    public R<Page<Employee>> pageQuery(Long pageSize, Long pageNum, String name) {
        // 1.创建分页对象
        Page<Employee> employeePage = new Page<>(pageNum,pageSize);
        // 2.是否需要根据名称查询
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper();
        if (!StringUtils.isEmpty(name)) {
            wrapper.like(Employee::getName,name);
        }
        wrapper.orderByDesc(Employee::getCreateTime);
        // 3.分页查询
        employeePage = baseMapper.selectPage(employeePage,wrapper);
        if (employeePage.getTotal() >= 1L) {
            // 脱敏处理
            List<Employee> records = employeePage.getRecords();
            records.forEach(e -> e.setPassword("*********"));
            return R.success(employeePage);
        }
        return null;
    }
}
