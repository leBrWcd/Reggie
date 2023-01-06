package com.lebrwcd.reggie.config;/**
 * @author lebrwcd
 * @date 2023/1/2
 * @note
 */

import com.lebrwcd.reggie.backend.interceptor.LoginInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * ClassName WebMvcConfig
 * Description TODO
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/2
 */
@Configuration
@Slf4j
public class WebMvcConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
        log.info("静态资源映射完成...");
    }

 /*   @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInteceptor = new LoginInterceptor();
        registry.addInterceptor(loginInteceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(
                        "/backend/page/login/login.html",
                        "/employee/login",
                        "/employee/logout",
                        "/backend/**",
                        "/front/**"
                );//添加不被拦截的路径,有很多静态资源
    }*/
}
