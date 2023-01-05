package com.lebrwcd.reggie.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.reggie.backend.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lebrwcd
 * @date 2023/1/5
 * @note 员工服务数据层
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
