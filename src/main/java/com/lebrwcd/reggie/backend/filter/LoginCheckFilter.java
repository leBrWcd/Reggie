package com.lebrwcd.reggie.backend.filter;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lebrwcd.reggie.backend.entity.User;
import com.lebrwcd.reggie.backend.service.UserService;
import com.lebrwcd.reggie.common.R;
import com.lebrwcd.reggie.common.util.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 检查用户是否已经完成登录
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter{

    // 路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Autowired
    private UserService userService;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        log.info("当前线程id: {}",Thread.currentThread().getId());

        //1、获取本次请求的URI
        String requestURI = request.getRequestURI();// /backend/index.html

        log.info("拦截到请求：{}",requestURI);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                // backend/* 和 front/** 这里是resource下的静态资源
                "/backend/**",
                "/front/**",
                "/common/**",
                // 下面两个是移动端发起的请求
                "/user/sendMsg",
                "/user/login"
        };

        //2、判断本次请求是否需要处理
        boolean check = check(urls, requestURI);

        //3、如果不需要处理，则直接放行
        if(check){
            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4、移动端========判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("userPhone") != null){
            String userPhone = (String) request.getSession().getAttribute("userPhone");
            log.info("移动端用户已登录，用户手机号为：{}",userPhone);
            // 根据手机号获取当前用户id
            LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(User::getPhone,userPhone);
            Long userId = userService.getOne(wrapper).getId();
            // 存入ThreadLocal
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }

        //4、管理端=======判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("employeeId") != null){
            Long userId = (Long) request.getSession().getAttribute("employeeId");
            log.info("管理端用户已登录，用户id为：{}",userId);
            // 存入ThreadLocal
            BaseContext.setCurrentId(userId);

            filterChain.doFilter(request,response);
            return;
        }

        log.info("用户未登录");
        //5、如果未登录则返回未登录结果，通过输出流方式向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径匹配，检查本次请求是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if(match){
                return true;
            }
        }
        return false;
    }
}
