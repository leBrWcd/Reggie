package com.lebrwcd.reggie.config;/**
 * @author lebrwcd
 * @date 2023/1/2
 * @note
 */

import com.lebrwcd.reggie.backend.interceptor.LoginInterceptor;
import com.lebrwcd.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

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
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...");
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(new JacksonObjectMapper());

        // 通过设置索引，让自己的转换器放在最前面，否则默认的jackson转换器会在最前面，用不上我们自己设置的转换器
        converters.add(0,messageConverter);
    }

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
