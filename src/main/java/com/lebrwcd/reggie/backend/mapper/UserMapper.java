package com.lebrwcd.reggie.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lebrwcd.reggie.backend.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lebrwcd
 * @date 2023/1/17
 * @note
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
