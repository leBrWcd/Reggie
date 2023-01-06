package com.lebrwcd.reggie.backend.interceptor;/**
 * @author lebrwcd
 * @date 2023/1/5
 * @note
 */

import com.alibaba.fastjson.JSON;
import com.lebrwcd.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ClassName LoginInteceptor
 * Description 登录拦截器
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/5
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        System.out.println("执行了拦截器的preHandle方法");
        log.info("访问路径: {}",request.getRequestURI());
        Object employeeId = request.getSession().getAttribute("employeeId");

        if (employeeId == null) {
            log.info("拦截到请求: {}",request.getRequestURI() );
            response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("拦截器 after...");
    }
}
