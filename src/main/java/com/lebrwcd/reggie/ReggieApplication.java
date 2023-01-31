package com.lebrwcd.reggie;/**
 * @author lebrwcd
 * @date 2023/1/2
 * @note
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;

/**
 * ClassName ReggieApplication
 * Description 主启动类
 *
 * @author lebr7wcd
 * @version 1.0
 * @date 2023/1/2
 */
@SpringBootApplication
@ServletComponentScan
@Slf4j
@EnableCaching
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class);
        log.info("------------------------项目启动成功--------------------------");
        log.info("后台管理系统首页地址：http://localhost:8080/backend/index.html");
        log.info("前台用户登录地址： http://localhost:8080/front/page/login.html");
    }
}
