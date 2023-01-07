package com.lebrwcd.reggie.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lebrwcd.reggie.backend.entity.Employee;
import com.lebrwcd.reggie.backend.vo.EmployeeQueryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author lebrwcd
 * @date 2023/1/5
 * @note 员工服务数据层
 */
@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
    Page<EmployeeQueryVO> selectPageByParam(Page<EmployeeQueryVO> employeePage, @Param("map") Map<String, String> map);
}
