package com.lebrwcd.reggie.backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lebrwcd.reggie.backend.dto.UserDTO;
import com.lebrwcd.reggie.backend.entity.User;
import com.lebrwcd.reggie.backend.mapper.UserMapper;
import com.lebrwcd.reggie.backend.service.UserService;
import com.lebrwcd.reggie.common.R;
import com.lebrwcd.reggie.common.util.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.Duration;
import java.util.Map;

import static com.lebrwcd.reggie.common.constant.RedisConstant.*;

/**
 * ClassName UserServiceImpl
 * Description 用户业务实现类
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/17
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public R<String> sendMessage(String phone) {
        // 1.手机号码是否为空
        if (!StringUtils.isEmpty(phone)) {
            // 2.生成随机的4位验证码
            Integer code = ValidateCodeUtils.generateValidateCode(4);
            log.info("生成的四位验证码为: {}",code);
            // 3.将code存入redis,设置验证码有效期
            redisTemplate.opsForValue().set(
                    APP_PREFIX + LOGIN_CODE + phone, String.valueOf(code), Duration.ofSeconds(LOGIN_CODE_TTL));

            return R.success("发送验证码成功！");
        }
        return null;
    }

    @Override
    public R<String> login(UserDTO user, HttpSession session) {

        // 1.拿到用户输入的验证码
        String code = String.valueOf(user.getCode());
        // 2.校验验证码是否正确
        String cacheCode = redisTemplate.opsForValue().get(APP_PREFIX + LOGIN_CODE + user.getPhone());
        if (!code.equals(cacheCode)) {
            // 2.1 不正确，直接返回错误信息
            return R.error("用户输入验证码不正确");
        }
        // 2.2 正确,查询数据库中用户是否存在
        User one = lambdaQuery().eq(User::getPhone, user.getPhone()).one();
        if (one == null) {
            // 3. 用户不存在，插入数据库，自动注册
            User newUser = new User();
            newUser.setPhone(user.getPhone());
            newUser.setStatus(1);
            baseMapper.insert(newUser);
        }
        // 4.用户存在，不需要处理
        // 将登录的手机号存入session
        session.setAttribute("userPhone",user.getPhone());
        return R.success("登录成功!");
    }
}
