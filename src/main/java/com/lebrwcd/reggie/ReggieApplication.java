package com.lebrwcd.reggie;/**
 * @author lebrwcd
 * @date 2023/1/2
 * @note
 */

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

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
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class);
        log.info("项目启动成功...");
    }
}
